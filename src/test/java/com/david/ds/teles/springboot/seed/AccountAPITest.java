package com.david.ds.teles.springboot.seed;

import com.david.ds.teles.springboot.seed.config.TestContainersConfig;
import com.david.ds.teles.springboot.seed.core.domain.Account;
import com.david.ds.teles.springboot.seed.dto.AccountDTO;
import com.david.ds.teles.springboot.seed.utils.i18n.AppMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@Testcontainers
@ContextConfiguration(initializers = TestContainersConfig.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class AccountAPITest {

	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	AppMessage messages;

	@Autowired
	WebTestClient webClient;

	static AccountDTO genAccountDTO() {
		return new AccountDTO(1l, "david.ds.teles@gmail.com", null);
	}

	@Test
	@Order(1)
	void should_insert_account_when_provided_a_valid_body() throws JsonProcessingException {
		AccountDTO result = genAccountDTO();
		Account givenAccount = new Account();
		givenAccount.setEmail("david.ds.teles@gmail.com");

		String body = objectMapper.writeValueAsString(result);

		webClient
			.post()
			.uri("/account")
			.bodyValue(givenAccount)
			.exchange()
			.expectStatus()
			.isCreated()
			.expectBody(String.class)
			.isEqualTo(body);
	}

	@Test
	void should_give_an_error_when_provide_invalid_email_provide()
		throws JsonProcessingException {
		Account givenAccount = new Account();
		givenAccount.setEmail("david.ds.teles@foo.com");

		String message = messages.getMessage(
			"invalid_email",
			new Object[] { givenAccount.getEmail() }
		);
		ObjectNode root = objectMapper.createObjectNode();
		root.put("status", 400);
		root.put("message", message);
		String body = objectMapper.writeValueAsString(root);

		webClient
			.post()
			.uri("/account")
			.bodyValue(givenAccount)
			.exchange()
			.expectStatus()
			.isBadRequest()
			.expectBody(String.class)
			.isEqualTo(body);
	}

	@Test
	@Order(2)
	void should_fetch_all_account() throws JsonProcessingException {
		AccountDTO dto = genAccountDTO();
		String body = objectMapper.writeValueAsString(List.of(dto));
		webClient
			.get()
			.uri("/account")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(String.class)
			.isEqualTo(body);
	}

	@Test
	@Order(3)
	void should_update_a_previous_created_account() throws JsonProcessingException {
		AccountDTO givenDto = genAccountDTO();
		givenDto.setProvider("google");

		webClient
			.put()
			.uri("/account/" + givenDto.getId())
			.bodyValue(givenDto)
			.exchange()
			.expectStatus()
			.isNoContent();

		String body = objectMapper.writeValueAsString(givenDto);

		webClient
			.get()
			.uri("/account/" + givenDto.getId())
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(String.class)
			.isEqualTo(body);
	}

	@Test
	void should_give_an_error_when_not_provide_a_body() throws JsonProcessingException {
		AccountDTO givenDto = genAccountDTO();
		givenDto.setProvider("google");

		String message = messages.getMessage("body_not_provided", null);
		ObjectNode root = objectMapper.createObjectNode();
		root.put("status", 400);
		root.put("message", message);
		String expectedResult = objectMapper.writeValueAsString(root);

		webClient
			.put()
			.uri("/account/" + givenDto.getId())
			.exchange()
			.expectStatus()
			.isBadRequest()
			.expectBody(String.class)
			.isEqualTo(expectedResult);
	}

	@Test
	@Order(4)
	void should_delete_a_previous_created_account() throws JsonProcessingException {
		webClient.delete().uri("/account/" + 1l).exchange().expectStatus().isNoContent();
	}

	@Test
	void should_give_an_404_when_id_not_exist() throws JsonProcessingException {
		Long givenId = 100l;
		String message = messages.getMessage(
			"not_found",
			new Object[] { "account", givenId }
		);
		ObjectNode root = objectMapper.createObjectNode();
		root.put("status", 404);
		root.put("message", message);

		String expectedResult = objectMapper.writeValueAsString(root);

		webClient
			.delete()
			.uri("/account/" + givenId)
			.exchange()
			.expectStatus()
			.isNotFound()
			.expectBody(String.class)
			.isEqualTo(expectedResult);
	}
}

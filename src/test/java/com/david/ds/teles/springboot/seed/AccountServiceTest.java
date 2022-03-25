package com.david.ds.teles.springboot.seed;

import com.david.ds.teles.springboot.seed.core.service.AccountService;
import com.david.ds.teles.springboot.seed.dao.AccountDAO;
import com.david.ds.teles.springboot.seed.utils.i18n.AppMessage;
import javax.validation.Validator;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class AccountServiceTest {
	@Mock
	AccountDAO dao;

	@Spy
	AppMessage message;

	@Spy
	Validator validator;

	@InjectMocks
	AccountService service;
	//	@Test
	//	void should_given_error_for_invalid_data() {
	//		System.out.println(message.getMessage("invalid_params"));
	//		service.create(null);
	//	}
}

package com.david.ds.teles.springboot.seed;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.david.ds.teles.springboot.seed.core.domain.Account;
import com.david.ds.teles.springboot.seed.core.service.AccountService;
import com.david.ds.teles.springboot.seed.dao.AccountDAO;
import com.david.ds.teles.springboot.seed.utils.TestHelpers;
import com.david.ds.teles.springboot.seed.utils.exceptions.MyExceptionError;
import com.david.ds.teles.springboot.seed.utils.i18n.AppMessage;
import java.time.OffsetDateTime;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

	private static final String INVALID_PARAMS =
		"the parameters provided are invalid. Check it and try again";

	@Mock
	AccountDAO dao;

	@Spy
	AppMessage message;

	@Spy
	Validator validator;

	@InjectMocks
	AccountService service;

	public AccountServiceTest() {
		message = TestHelpers.getAppMessageInstance();
		validator = TestHelpers.getValidatorInstance();
	}

	@Test
	void should_create_an_account() {
		// given
		String email = "david.ds.teles@gmail.com";
		Account givenAccount = new Account();
		givenAccount.setEmail(email);

		// when
		when(dao.save(givenAccount))
			.thenReturn(new Account(1l, email, null, OffsetDateTime.now()));
		Account account = service.create(givenAccount);

		// then
		verify(dao, times(1)).save(givenAccount);
		assertThat(account).isNotNull();
		assertThat(account.getId()).isNotNull();
		assertThat(account.getId()).isGreaterThan(0);
		assertThat(account.getId()).isEqualTo(1);
	}

	@Test
	void should_give_error_when_try_create_account_with_invalid_data() {
		try {
			service.create(null);
			Assert.fail("should not reach here");
		} catch (Throwable e) {
			assertThat(e.getMessage()).isEqualTo(INVALID_PARAMS);
		}
	}

	@Test
	void should_give_error_when_try_create_account_with_invalid_email_pattern() {
		try {
			service.create(new Account(null, "david.ds.teles@foo.com", null, null));
			Assert.fail("should not reach here");
		} catch (Throwable e) {
			assertThat(e.getMessage()).isEqualTo("email invalid for account");
		}
	}

	@Test
	void should_give_error_when_try_update_account_with_invalid_id() {
		// given
		Account givenAccount = new Account(
			0l,
			"david.ds.teles@gmail.com",
			null,
			OffsetDateTime.now()
		);

		try {
			service.update(givenAccount);
			Assert.fail("should not reach here");
		} catch (MyExceptionError e) {
			assertThat(e.getMessage()).isEqualTo("update invalid for account");
		} catch (Throwable e) {
			Assert.fail("unexpected exception caught");
		}
	}

	@Test
	void should_give_error_when_try_update_account_with_invalid_data() {
		// given
		Account givenAccount = new Account(
			1l,
			"david.ds.teles@gmail.com",
			null,
			OffsetDateTime.now()
		);

		// when
		when(dao.existsById(anyLong())).thenReturn(true);
		when(dao.getById(anyLong())).thenReturn(givenAccount);

		try {
			service.update(givenAccount);
			Assert.fail("should not reach here");
		} catch (ConstraintViolationException e) {
			// then
			assertThat(e.getMessage()).isEqualTo("provider: must not be blank");
		} catch (Throwable e) {
			Assert.fail("unexpected exception caught");
		}

		// then
		verify(dao, times(1)).existsById(givenAccount.getId());
		verify(dao, times(1)).getById(givenAccount.getId());
	}

	@Test
	void should_give_error_when_try_update_an_unexistent_account() {
		// given
		Account givenAccount = new Account(
			1l,
			"david.ds.teles@gmail.com",
			null,
			OffsetDateTime.now()
		);

		// when
		when(dao.existsById(anyLong())).thenReturn(false);

		try {
			service.update(givenAccount);
			Assert.fail("should not reach here");
		} catch (MyExceptionError e) {
			// then
			assertThat(e.getStatus()).isEqualTo(404);
		} catch (Throwable e) {
			Assert.fail("unexpected exception caught");
		}

		// then
		verify(dao, times(1)).existsById(givenAccount.getId());
	}

	@Test
	void should_update_an_account() {
		// given
		Account givenAccount = new Account(
			1l,
			"david.ds.teles@gmail.com",
			"google",
			OffsetDateTime.now()
		);

		// when
		when(dao.existsById(anyLong())).thenReturn(true);
		when(dao.getById(anyLong())).thenReturn(givenAccount);

		service.update(givenAccount);

		// then
		verify(dao, times(1)).existsById(givenAccount.getId());
		verify(dao, times(1)).getById(givenAccount.getId());
		verify(dao, times(1)).save(any());
	}

	@Test
	void should_delete_an_account() {
		// given
		Long givenId = 1l;

		// when
		when(dao.existsById(anyLong())).thenReturn(true);

		service.delete(givenId);

		// then
		verify(dao, times(1)).existsById(givenId);
		verify(dao, times(1)).delete(any());
	}

	@Test
	void should_give_error_when_try_delete__an_unexistent_account() {
		// given
		Long givenId = 1l;

		// when
		when(dao.existsById(anyLong())).thenReturn(false);

		try {
			service.delete(givenId);
		} catch (MyExceptionError e) {
			// then
			assertThat(e.getStatus()).isEqualTo(404);
		} catch (Throwable e) {
			Assert.fail("unexpected exception caught");
		}

		// then
		verify(dao, times(1)).existsById(givenId);
	}
}

package com.david.ds.teles.springboot.seed.core.service;

import com.david.ds.teles.springboot.seed.core.domain.Account;
import com.david.ds.teles.springboot.seed.dao.AccountDAO;
import com.david.ds.teles.springboot.seed.utils.exceptions.MyExceptionError;
import com.david.ds.teles.springboot.seed.utils.i18n.AppMessage;
import com.david.ds.teles.springboot.seed.utils.logging.DefaultLogging.LogOperation;
import com.david.ds.teles.springboot.seed.utils.validator.MyValidatorGroups;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@LogOperation
@RequiredArgsConstructor
public class AccountService {
	private final AccountDAO dao;

	private final Validator validator;

	private final AppMessage messages;

	@Transactional
	public Account create(Account account) {
		if (account == null) throw new MyExceptionError(messages.getMessage("invalid_params"));

		account.checkCreateParams();

		Set<ConstraintViolation<Account>> violations = validator.validate(
			account,
			MyValidatorGroups.Create.class
		);

		if (!violations.isEmpty()) throw new ConstraintViolationException(violations);

		account = dao.save(account);

		return account;
	}

	@Transactional
	public Account update(Account account) {
		account.checkUpdateParams();

		boolean isExists = dao.existsById(account.getId());

		if (!isExists) throw new MyExceptionError(
			messages.getMessage("not_found", new Object[] { "account", account.getId() })
		);

		Account toUpdate = dao.getById(account.getId());
		toUpdate.setProvider(account.getProvider());

		Set<ConstraintViolation<Account>> violations = validator.validate(
			account,
			MyValidatorGroups.Update.class
		);

		if (!violations.isEmpty()) throw new ConstraintViolationException(violations);

		toUpdate = dao.save(toUpdate);

		return toUpdate;
	}

	@Transactional
	public Account fetch(Long id) {
		Account account = new Account(id);
		account.checkIdValidty();

		account = dao.getById(id);
		return account;
	}

	@Transactional
	public List<Account> fetchAll() {
		List<Account> result = dao.findAll();
		return result;
	}

	@Transactional
	public void delete(Long id) {
		Account account = new Account(id);
		account.checkIdValidty();
		boolean isExists = dao.existsById(id);

		if (isExists) dao.delete(account); else throw new MyExceptionError(
			messages.getMessage("not_found", new Object[] { "account", id })
		);
	}
}

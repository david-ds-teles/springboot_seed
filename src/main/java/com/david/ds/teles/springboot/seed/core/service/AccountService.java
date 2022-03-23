package com.david.ds.teles.springboot.seed.core.service;

import com.david.ds.teles.springboot.seed.core.domain.Account;
import com.david.ds.teles.springboot.seed.dao.AccountDAO;
import com.david.ds.teles.springboot.seed.utils.exceptions.MyExceptionError;
import com.david.ds.teles.springboot.seed.utils.i18n.AppMessage;
import com.david.ds.teles.springboot.seed.utils.logging.DefaultLogging.LogOperation;
import com.david.ds.teles.springboot.seed.utils.validator.MyValidatorGroups;
import java.util.Set;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@LogOperation
public class AccountService {
	private AccountDAO dao;

	private Validator validator;

	private AppMessage messages;

	@Autowired
	public AccountService(AccountDAO dao, Validator validator, AppMessage messages) {
		this.dao = dao;
		this.validator = validator;
		this.messages = messages;
	}

	public Account create(Account account) {
		account.create(messages);

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
		account.update(messages);

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
		if (id == null || id <= 0) throw new MyExceptionError(messages.getMessage("invalid_params"));

		Account account = dao.getById(id);
		return account;
	}
}

package com.david.ds.teles.springboot.seed.api;

import com.david.ds.teles.springboot.seed.api.spec.AccountApiSpec;
import com.david.ds.teles.springboot.seed.core.domain.Account;
import com.david.ds.teles.springboot.seed.core.service.AccountService;
import com.david.ds.teles.springboot.seed.dto.AccountDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountAPI implements AccountApiSpec {

	@Autowired
	private AccountService service;

	@Override
	public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO dto) {
		Account account = service.create(dto.toEntity());
		AccountDTO result = new AccountDTO(account);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@Override
	public ResponseEntity<Void> update(
		@PathVariable("id") Long id,
		@RequestBody AccountDTO dto
	) {
		if (dto.getId() == null) dto.setId(id);

		service.update(dto.toEntity());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Override
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Override
	public ResponseEntity<AccountDTO> fetch(@PathVariable("id") Long id) {
		Account account = service.fetch(id);
		AccountDTO result = new AccountDTO(account);
		return ResponseEntity.ok(result);
	}

	@Override
	public ResponseEntity<List<AccountDTO>> all() {
		List<Account> accounts = service.fetchAll();
		List<AccountDTO> result = accounts
			.stream()
			.map(a -> new AccountDTO(a))
			.collect(Collectors.toList());
		return ResponseEntity.ok(result);
	}
}

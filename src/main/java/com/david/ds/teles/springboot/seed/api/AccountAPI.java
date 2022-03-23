package com.david.ds.teles.springboot.seed.api;

import com.david.ds.teles.springboot.seed.core.domain.Account;
import com.david.ds.teles.springboot.seed.core.service.AccountService;
import com.david.ds.teles.springboot.seed.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountAPI {
	@Autowired
	private AccountService service;

	@PostMapping
	public ResponseEntity<AccountDTO> create(@RequestBody Account account) {
		account = service.create(account);
		AccountDTO result = new AccountDTO(account);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@PutMapping
	public ResponseEntity<Void> update(@RequestBody Account account) {
		service.update(account);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("/{id:[\\d]+}")
	public ResponseEntity<AccountDTO> fetch(@PathVariable("id") Long id) {
		Account account = service.fetch(id);
		AccountDTO result = new AccountDTO(account);
		return ResponseEntity.ok(result);
	}
}

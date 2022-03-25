package com.david.ds.teles.springboot.seed.api;

import com.david.ds.teles.springboot.seed.core.domain.Account;
import com.david.ds.teles.springboot.seed.core.service.AccountService;
import com.david.ds.teles.springboot.seed.dto.AccountDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO dto) {
		Account account = service.create(dto.toEntity());
		AccountDTO result = new AccountDTO(account);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@PutMapping("/{id:[\\d]+}")
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody AccountDTO dto) {
		if (dto.getId() == null) dto.setId(id);

		service.update(dto.toEntity());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/{id:[\\d]+}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("/{id:[\\d]+}")
	public ResponseEntity<AccountDTO> fetch(@PathVariable("id") Long id) {
		Account account = service.fetch(id);
		AccountDTO result = new AccountDTO(account);
		return ResponseEntity.ok(result);
	}

	@GetMapping
	public ResponseEntity<List<AccountDTO>> all() {
		List<Account> accounts = service.fetchAll();
		List<AccountDTO> result = accounts
			.stream()
			.map(a -> new AccountDTO(a))
			.collect(Collectors.toList());
		return ResponseEntity.ok(result);
	}
}

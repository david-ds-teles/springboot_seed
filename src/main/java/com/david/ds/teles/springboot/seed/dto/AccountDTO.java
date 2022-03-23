package com.david.ds.teles.springboot.seed.dto;

import com.david.ds.teles.springboot.seed.core.domain.Account;
import com.david.ds.teles.springboot.seed.utils.converters.EntityConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AccountDTO implements EntityConverter<Account> {
	private Long id;

	private String email;

	private String provider;

	public AccountDTO(Account account) {
		this.convert(account);
	}

	@Override
	public void convert(Account entity) {
		if (entity == null) return;

		this.id = entity.getId();
		this.email = entity.getEmail();
		this.provider = entity.getProvider();
	}
}

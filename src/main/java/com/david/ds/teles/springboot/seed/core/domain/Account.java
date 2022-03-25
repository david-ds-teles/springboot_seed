package com.david.ds.teles.springboot.seed.core.domain;

import com.david.ds.teles.springboot.seed.utils.exceptions.MyExceptionError;
import com.david.ds.teles.springboot.seed.utils.validator.MyValidatorGroups;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.OffsetDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(schema = "spring", name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Email
	private String email;

	@NotBlank(groups = { MyValidatorGroups.Update.class })
	private String provider;

	@UpdateTimestamp
	@PastOrPresent(groups = { MyValidatorGroups.Update.class })
	private OffsetDateTime updatedAt;

	public Account(Long id) {
		this.id = id;
	}

	public void checkIdValidty() {
		if (this.id == null || this.id <= 0) throw new MyExceptionError(
			"id invalid for account",
			"invalid_params",
			null
		);
	}

	public void checkCreateParams() {
		if (this.email == null) throw new MyExceptionError(
			"email invalid for account",
			"invalid_email",
			new Object[] { email }
		);

		Pattern p = Pattern.compile("^.+@foo\\.com");
		Matcher m = p.matcher(this.email);

		if (m.matches()) throw new MyExceptionError(
			"email invalid for account",
			"invalid_email",
			new Object[] { email }
		);
	}

	public void checkUpdateParams() {
		if (this.id == null || id <= 0) throw new MyExceptionError(
			"update invalid for account",
			"invalid_params",
			null
		);
	}
}

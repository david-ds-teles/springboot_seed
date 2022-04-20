package com.david.ds.teles.springboot.seed.api.spec;

import com.david.ds.teles.springboot.seed.dto.AccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Account", description = "REST API for account information.")
public interface AccountApiSpec {
	@Operation(summary = "Create an account", description = "Create a new account")
	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "${api.rsp.ok}"),
			@ApiResponse(responseCode = "400", description = "${api.rsp.badRequest}"),
			@ApiResponse(responseCode = "404", description = "${api.rsp.notFound}"),
		}
	)
	@PostMapping
	public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO dto);

	@Operation(
		summary = "Update an account",
		description = "Update a existent account with further information"
	)
	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "204", description = "${api.rsp.ok}"),
			@ApiResponse(responseCode = "400", description = "${api.rsp.badRequest}"),
			@ApiResponse(responseCode = "404", description = "${api.rsp.notFound}"),
		}
	)
	@PutMapping("/{id:[\\d]+}")
	public ResponseEntity<Void> update(
		@PathVariable("id") Long id,
		@RequestBody AccountDTO dto
	);

	@Operation(summary = "Delete an account", description = "Delete a existent account")
	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "204", description = "${api.rsp.ok}"),
			@ApiResponse(responseCode = "400", description = "${api.rsp.badRequest}"),
			@ApiResponse(responseCode = "404", description = "${api.rsp.notFound}"),
		}
	)
	@DeleteMapping("/{id:[\\d]+}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id);

	@Operation(
		summary = "Fetch an account",
		description = "Fetch a existent account using its ID"
	)
	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "${api.rsp.ok}"),
			@ApiResponse(responseCode = "400", description = "${api.rsp.badRequest}"),
			@ApiResponse(responseCode = "404", description = "${api.rsp.notFound}"),
		}
	)
	@GetMapping("/{id:[\\d]+}")
	public ResponseEntity<AccountDTO> fetch(@PathVariable("id") Long id);

	@Operation(summary = "List accounts", description = "List all accounts")
	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "${api.rsp.ok}"),
			@ApiResponse(responseCode = "400", description = "${api.rsp.badRequest}"),
			@ApiResponse(responseCode = "404", description = "${api.rsp.notFound}"),
		}
	)
	@GetMapping
	public ResponseEntity<List<AccountDTO>> all();
}

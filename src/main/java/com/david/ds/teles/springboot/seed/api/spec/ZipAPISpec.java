package com.david.ds.teles.springboot.seed.api.spec;

import com.david.ds.teles.springboot.seed.client.Zip;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Zip", description = "API zip code")
public interface ZipAPISpec {
	@Operation(
		summary = "Fetch by zip code",
		description = "Fetch address information using a ZIP code"
	)
	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "${api.rsp.ok}"),
			@ApiResponse(responseCode = "400", description = "${api.rsp.badRequest}"),
			@ApiResponse(responseCode = "404", description = "${api.rsp.notFound}"),
		}
	)
	@GetMapping("/{zip}")
	public ResponseEntity<Zip> fetch(@PathVariable("zip") String zip);
}

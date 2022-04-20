package com.david.ds.teles.springboot.seed.api;

import com.david.ds.teles.springboot.seed.api.spec.ZipAPISpec;
import com.david.ds.teles.springboot.seed.client.Zip;
import com.david.ds.teles.springboot.seed.client.ZipService;
import com.david.ds.teles.springboot.seed.utils.logging.DefaultLogging.LogOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zip")
@LogOperation
public class ZipAPI implements ZipAPISpec {

	@Autowired
	private ZipService service;

	@Override
	public ResponseEntity<Zip> fetch(@PathVariable("zip") String zip) {
		Zip result = service.fetch(zip);
		return ResponseEntity.ok(result);
	}
}

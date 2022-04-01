package com.david.ds.teles.springboot.seed.api;

import com.david.ds.teles.springboot.seed.provider.bar.BarServiceProvider;
import com.david.ds.teles.springboot.seed.provider.fancy.SomeFancyServiceProvider;
import com.david.ds.teles.springboot.seed.provider.foo.FooServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dynamic")
public class DynamicProvidersAPI {

	@Autowired
	private SomeFancyServiceProvider fancyService;

	@Autowired
	private BarServiceProvider barService;

	@Autowired
	private FooServiceProvider fooService;

	@GetMapping("/fancy")
	public ResponseEntity<String> testFancy() {
		String message = fancyService.doStuff();

		return ResponseEntity.ok(message);
	}

	@GetMapping("/bar")
	public ResponseEntity<String> testBar() {
		String message = barService.doStuff();

		return ResponseEntity.ok(message);
	}

	@GetMapping("/foo")
	public ResponseEntity<String> testFoo() {
		String message = fooService.doStuff();

		return ResponseEntity.ok(message);
	}
}

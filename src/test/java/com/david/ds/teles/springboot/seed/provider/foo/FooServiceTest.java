package com.david.ds.teles.springboot.seed.provider.foo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class FooServiceTest {

	@Autowired
	FooServiceProvider service;

	@Test
	void should_be_the_production_version() {
		assertEquals(
			"this is the foo service provider used in PRODUCTION",
			service.doStuff()
		);
	}
}

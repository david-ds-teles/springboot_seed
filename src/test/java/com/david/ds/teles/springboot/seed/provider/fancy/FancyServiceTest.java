package com.david.ds.teles.springboot.seed.provider.fancy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FancyServiceTest {

	@Autowired
	SomeFancyServiceProvider service;

	@Test
	void should_be_the_production_version() {
		assertEquals(
			"this is the fancy service provider used in PRODUCTION",
			service.doStuff()
		);
	}
}

package com.david.ds.teles.springboot.seed.provider.fancy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class MockedFancyServiceTest {

	@Autowired
	SomeFancyServiceProvider service;

	@Test
	void should_be_the_mocked_version() {
		assertEquals(
			"this is the MOCKED fancy service provider used in DEVELOPMENT",
			service.doStuff()
		);
	}
}

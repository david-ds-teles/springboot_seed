package com.david.ds.teles.springboot.seed.provider.foo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
class MockedFooServiceProvider implements FooServiceProvider {

	static final Logger LOG = LoggerFactory.getLogger(MockedFooServiceProvider.class);

	@Override
	public String doStuff() {
		String foo = "this is the MOCKED foo service provider used in DEVELOPMENT";
		LOG.warn(foo);
		return foo;
	}
}

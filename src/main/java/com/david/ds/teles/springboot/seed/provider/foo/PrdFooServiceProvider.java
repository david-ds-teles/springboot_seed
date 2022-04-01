package com.david.ds.teles.springboot.seed.provider.foo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!dev")
class PrdFooServiceProvider implements FooServiceProvider {

	static final Logger LOG = LoggerFactory.getLogger(PrdFooServiceProvider.class);

	@Override
	public String doStuff() {
		String foo = "this is the foo service provider used in PRODUCTION";
		LOG.warn(foo);
		return foo;
	}
}

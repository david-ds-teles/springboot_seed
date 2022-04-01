package com.david.ds.teles.springboot.seed.provider.bar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class MockedBarServiceProvider implements BarServiceProvider {

	static final Logger LOG = LoggerFactory.getLogger(MockedBarServiceProvider.class);

	@Override
	public String doStuff() {
		String foo = "this is the MOCKED bar service provider used in DEVELOPMENT";
		LOG.warn(foo);
		return foo;
	}
}

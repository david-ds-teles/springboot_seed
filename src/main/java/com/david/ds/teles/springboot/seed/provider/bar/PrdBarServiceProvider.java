package com.david.ds.teles.springboot.seed.provider.bar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class PrdBarServiceProvider implements BarServiceProvider {

	static final Logger LOG = LoggerFactory.getLogger(PrdBarServiceProvider.class);

	@Override
	public String doStuff() {
		String foo = "this is the bar service provider used in PRODUCTION";
		LOG.warn(foo);
		return foo;
	}
}

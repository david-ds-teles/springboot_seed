package com.david.ds.teles.springboot.seed.provider.fancy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component("mocked-fancy-service")
@ConditionalOnProperty(name = "fancy.service.mocked", havingValue = "true")
class FancyMockedServiceProvider implements SomeFancyServiceProvider {

	static final Logger LOG = LoggerFactory.getLogger(FancyMockedServiceProvider.class);

	@Override
	public String doStuff() {
		String foo = "this is the MOCKED fancy service provider used in DEVELOPMENT";
		LOG.warn(foo);
		return foo;
	}
}

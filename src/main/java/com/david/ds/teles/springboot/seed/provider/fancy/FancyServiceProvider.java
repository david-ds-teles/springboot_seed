package com.david.ds.teles.springboot.seed.provider.fancy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
	name = "fancy.service.mocked",
	havingValue = "false",
	matchIfMissing = true
)
class FancyServiceProvider implements SomeFancyServiceProvider {

	static final Logger LOG = LoggerFactory.getLogger(FancyServiceProvider.class);

	@Override
	public String doStuff() {
		String foo = "this is the fancy service provider used in PRODUCTION";
		LOG.info(foo);
		return foo;
	}
}

package com.david.ds.teles.springboot.seed.provider.bar;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
public class DynamicAutowiredBarService {

	private final BeanFactory beanFactory;

	private final boolean isMocked;

	private BarServiceProvider service;

	@Autowired
	public DynamicAutowiredBarService(
		@Value("${bar.service.mocked: false}") boolean isMocked,
		BeanFactory beanFactory
	) {
		this.beanFactory = beanFactory;
		this.isMocked = isMocked;
	}

	@Bean
	@Primary
	public BarServiceProvider getService() {
		if (this.service != null) return this.service;

		if (isMocked) {
			this.service = this.beanFactory.getBean(MockedBarServiceProvider.class);
		} else {
			this.service = this.beanFactory.getBean(PrdBarServiceProvider.class);
		}

		return this.service;
	}
}

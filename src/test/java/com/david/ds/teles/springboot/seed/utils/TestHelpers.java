package com.david.ds.teles.springboot.seed.utils;

import com.david.ds.teles.springboot.seed.utils.i18n.AppMessage;
import com.david.ds.teles.springboot.seed.utils.i18n.DefaultAppMessages;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.springframework.context.support.ResourceBundleMessageSource;

public abstract class TestHelpers {

	public static AppMessage getAppMessageInstance() {
		ResourceBundleMessageSource sourceBundle = new ResourceBundleMessageSource();
		sourceBundle.setBasenames("lang/messages");
		return new DefaultAppMessages(sourceBundle);
	}

	public static Validator getValidatorInstance() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		return factory.getValidator();
	}
}

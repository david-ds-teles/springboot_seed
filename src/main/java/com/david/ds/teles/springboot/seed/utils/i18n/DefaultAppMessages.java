package com.david.ds.teles.springboot.seed.utils.i18n;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class DefaultAppMessages implements AppMessage {

	private MessageSource source;

	private Locale locale = Locale.getDefault();

	public DefaultAppMessages(MessageSource source) {
		this.source = source;
	}

	@Override
	public String getMessage(String key, Object[] params) {
		return source.getMessage(key, params, locale);
	}

	@Override
	public String getMessage(String key) {
		return source.getMessage(key, null, locale);
	}

	@Override
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}

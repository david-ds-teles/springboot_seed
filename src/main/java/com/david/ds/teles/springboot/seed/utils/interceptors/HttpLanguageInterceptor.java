package com.david.ds.teles.springboot.seed.utils.interceptors;

import com.david.ds.teles.springboot.seed.utils.i18n.AppMessage;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Component
public class HttpLanguageInterceptor implements HandlerInterceptor {
	@Autowired
	private AppMessage appMessage;

	private SessionLocaleResolver localeResolver = new SessionLocaleResolver();

	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	)
		throws Exception {
		String lang = request.getHeader("accept-language");
		Locale locale = Locale.getDefault();

		if (lang == null) lang = request.getParameter("lang");

		if (lang != null) locale = new Locale(lang);

		localeResolver.setDefaultLocale(locale);
		appMessage.setLocale(locale);

		return true;
	}

	@Bean
	public LocaleResolver localeResolver() {
		return this.localeResolver;
	}
}

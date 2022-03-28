package com.david.ds.teles.springboot.seed.utils.logging;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DefaultLogging {

	private Logger log = LoggerFactory.getLogger(DefaultLogging.class);

	@Around(
		"within(@DefaultLogging.LogOperation *) || @annotation(DefaultLogging.LogOperation)"
	)
	public Object log(ProceedingJoinPoint point) throws Throwable {
		log.info("starting {}", point.getSignature());
		Object result = point.proceed();
		log.info(
			"ending {} -> with result: {}",
			point.getSignature(),
			result != null ? result.toString() : ""
		);
		return result;
	}

	@Retention(RUNTIME)
	@Target({ TYPE, METHOD })
	public @interface LogOperation {
	}
}

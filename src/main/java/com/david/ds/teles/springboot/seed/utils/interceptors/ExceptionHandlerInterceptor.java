package com.david.ds.teles.springboot.seed.utils.interceptors;

import com.david.ds.teles.springboot.seed.utils.exceptions.MyExceptionError;
import com.david.ds.teles.springboot.seed.utils.i18n.AppMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerInterceptor extends ResponseEntityExceptionHandler {
	private Logger log = LoggerFactory.getLogger(ExceptionHandlerInterceptor.class);

	@Autowired
	private AppMessage messages;

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
		log.error("default handleConstraintViolation error handler", ex);

		List<String> errors = new ArrayList<>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			String field = violation.getPropertyPath().toString();
			String temp = violation.getMessage();
			errors.add(field + ": " + temp);
		}

		ErrorResponse rsp = new ErrorResponse(
			HttpStatus.BAD_REQUEST.value(),
			messages.getMessage("invalid_request"),
			errors
		);
		return ResponseEntity.internalServerError().body(rsp);
	}

	@ExceptionHandler(MyExceptionError.class)
	public ResponseEntity<ErrorResponse> myExceptionHandler(MyExceptionError ex) {
		log.error("default throwable error handler", ex);

		ErrorResponse rsp = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rsp);
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ErrorResponse> handleThrowableException(Throwable ex) {
		log.error("default throwable error handler", ex);

		ErrorResponse rsp = new ErrorResponse(
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			messages.getMessage("internal_server_error")
		);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(rsp);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
		HttpMessageNotReadableException ex,
		HttpHeaders headers,
		HttpStatus status,
		WebRequest request
	) {
		log.error("default handleHttpMessageNotReadable handler", ex);

		ErrorResponse rsp = new ErrorResponse(
			HttpStatus.BAD_REQUEST.value(),
			messages.getMessage("body_not_provided")
		);
		return ResponseEntity.internalServerError().body(rsp);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
		Exception ex,
		Object body,
		HttpHeaders headers,
		HttpStatus status,
		WebRequest request
	) {
		log.error("default exception error handler", ex);

		ErrorResponse rsp = new ErrorResponse(
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			messages.getMessage("internal_server_error")
		);
		return ResponseEntity.internalServerError().body(rsp);
	}

	@JsonInclude(Include.NON_NULL)
	static class ErrorResponse {
		public int status;
		public String message;
		public List<String> errors;

		public ErrorResponse(int status, String message) {
			this.status = status;
			this.message = message;
		}

		public ErrorResponse(int status, String message, List<String> errors) {
			this.status = status;
			this.message = message;
			this.errors = errors;
		}
	}
}

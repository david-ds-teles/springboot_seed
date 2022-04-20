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
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerInterceptor extends ResponseEntityExceptionHandler {

	private Logger log = LoggerFactory.getLogger(ExceptionHandlerInterceptor.class);

	@Autowired
	private AppMessage messages;

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleConstraintViolation(
		ConstraintViolationException ex
	) {
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
		return ResponseEntity.badRequest().body(rsp);
	}

	@ExceptionHandler(MyExceptionError.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> myExceptionHandler(MyExceptionError ex) {
		log.info("default throwable error handler");

		String message = extractMessageKeyValue(ex);

		log.error(message, ex);

		ErrorResponse rsp = new ErrorResponse(ex.getStatus(), message);
		return ResponseEntity.status(ex.getStatus()).body(rsp);
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorResponse> handleThrowableException(Throwable ex) {
		log.error("default throwable error handler", ex);

		ErrorResponse rsp = new ErrorResponse(
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			messages.getMessage("internal_server_error")
		);
		return ResponseEntity.internalServerError().body(rsp);
	}

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
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

		return ResponseEntity.badRequest().body(rsp);
	}

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
		HttpRequestMethodNotSupportedException ex,
		HttpHeaders headers,
		HttpStatus status,
		WebRequest request
	) {
		log.error("default handleHttpRequestMethodNotSupported handler", ex);

		ErrorResponse rsp = new ErrorResponse(
			HttpStatus.BAD_REQUEST.value(),
			messages.getMessage("http_method_not_supported")
		);

		return ResponseEntity.badRequest().body(rsp);
	}

	@Override
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
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

	private String extractMessageKeyValue(MyExceptionError ex) {
		String message = ex.getMessage();

		if (ex.getMessageKey() != null) {
			message = messages.getMessage(ex.getMessageKey(), ex.getMessageArgs());
		}

		return message;
	}

	@JsonInclude(Include.NON_NULL)
	public static class ErrorResponse {

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

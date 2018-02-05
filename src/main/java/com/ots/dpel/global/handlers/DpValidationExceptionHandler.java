package com.ots.dpel.global.handlers;

import com.ots.dpel.global.exceptions.DpRuntimeException;
import com.ots.dpel.global.exceptions.DpValidationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * @author sdimitriadis
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DpValidationExceptionHandler extends DpExceptionHandler {

	@ExceptionHandler(value = { DpValidationException.class })
	protected ResponseEntity<Object> handleConflict(DpRuntimeException ex, WebRequest request) {
		return super.handleConflict(ex, request, HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
package com.aoede.commons.base.config;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.exceptions.BadRequestException;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.exceptions.MethodNotAllowedException;
import com.aoede.commons.base.exceptions.NotFoundException;

@RestControllerAdvice
public class GenericExceptionHandler extends BaseComponent {

	/**
	 * Universal handler for all different api statuses
	 */
	@ExceptionHandler(GenericException.class)
	public final ResponseEntity<?> handleAllApiErrors(final GenericException ex) {
		return generateResponse (ex);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public final ResponseEntity<?> handleNoHandlerFoundException (final NoHandlerFoundException ex) {
		return generateResponse (
			new NotFoundException(ex.getMessage())
		);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public final ResponseEntity<?> handleHttpRequestMethodNotSupportedException (final HttpRequestMethodNotSupportedException ex) {
		return generateResponse (
			new MethodNotAllowedException(ex.getMessage())
		);
	}

	@ExceptionHandler(MissingRequestHeaderException.class)
	public final ResponseEntity<?> handleMissingRequestHeaderException (final MissingRequestHeaderException ex) {
		return generateResponse (
			new BadRequestException(ex.getMessage())
		);
	}

	@ExceptionHandler(value=MethodArgumentNotValidException.class)
	public final ResponseEntity<?> handleException(MethodArgumentNotValidException ex) {
		BadRequestException bre = new BadRequestException ("Validation erros");

		// build validation info
		bre.info = ex.getBindingResult().getFieldErrors().stream()
			.map(err -> err.getField() + " for value " +  err.getRejectedValue() + " : " + err.getDefaultMessage())
			.distinct()
			.collect(Collectors.toList());

		return generateResponse (bre);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<?> handleInternalServerException (final Exception ex) {
		return generateResponse (
			new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage())
		);
	}

	private ResponseEntity<?> generateResponse (final GenericException ex) {
		return new ResponseEntity<GenericException> (
			ex,
			ex.status
		);
	}
}




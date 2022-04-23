package com.aoede.commons.base.config;

import java.util.stream.Collectors;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.exceptions.BadRequestException;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.exceptions.GenericExceptionContainer;
import com.aoede.commons.base.exceptions.MethodNotAllowedException;
import com.aoede.commons.base.exceptions.NotFoundException;
import com.aoede.commons.base.exceptions.UnauthorizedException;
import com.aoede.commons.base.exceptions.ValidationFailure;

/**
 * Implementation of a generic exception handler to handle system exceptions
 *
 * Handled exception
 *  - GenericException
 *    Those are the "business" exceptions indicating an issue completing the requested task
 *
 *  - NoHandlerFoundException
 *    This is the error raised by spring in case no handler is found for the request
 *    Handler will convert this error to NotFoundException (GenericException) and return the error
 *
 *  - HttpRequestMethodNotSupportedException
 *    This is the error raised by spring in case the handler does not support the request method
 *    Handler will convert this error to MethodNotAllowedException (GenericException) and return the error
 *
 *  - MissingRequestHeaderException
 *    This error is generated by spring in case a header that is required to process a request is missing
 *
 *  - HttpMessageConversionException
 *    This is the error raised by spring in case the request body is not able to be converted to the specified class
 *
 *  - TypeMismatchException
 *    This is generated by spring in case any provided type is able to be converted
 *
 *  - MethodArgumentNotValidException
 *    This is generated by spring in case any validation checks fail
 *
 *  - HttpMediaTypeNotAcceptableException
 *    This is generated by spring in case no supported media type is accepted by sender
 *
 *  - MethodArgumentTypeMismatchException
 *    This is generated by spring in case method argument type mismatch is found
 *
 *  - AuthenticationException
 *    This is generated in case the user is not able to be authenticated
 *
 *  - Exception
 *    This is a generic handler for all exceptions not being processed by any of the other handlers
 *    will result in an internal server error being generated
 */
@RestControllerAdvice
public class GenericExceptionHandler extends BaseComponent {

	/**
	 * Those are the "business" exceptions indicating an issue completing the requested task
	 */
	@ExceptionHandler(GenericException.class)
	public final ResponseEntity<?> handleAllApiErrors(final GenericException ex) {
		return generateResponse (ex);
	}

	/**
	 * A GenericExceptionContainer is essentially a GenericException thrown as a runtime exception
	 * to be used in cases where adding the GenericException in the method signature is not possible
	 */
	@ExceptionHandler(GenericExceptionContainer.class)
	public final ResponseEntity<?> handleGenericExceptionContainer (final GenericExceptionContainer ex) {
		return generateResponse(ex.getException());
	}

	/**
	 * This is the error raised by spring in case no handler is found for the request
	 * Handler will convert this error to NotFoundException (GenericException) and return the error
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public final ResponseEntity<?> handleNoHandlerFoundException (final NoHandlerFoundException ex) {
		return generateResponse (
			new NotFoundException(ex.getMessage())
		);
	}

	/**
	 * This is the error raised by spring in case the handler does not support the request method
	 * Handler will convert this error to MethodNotAllowedException (GenericException) and return the error
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public final ResponseEntity<?> handleHttpRequestMethodNotSupportedException (final HttpRequestMethodNotSupportedException ex) {
		return generateResponse (
			new MethodNotAllowedException(ex.getMessage())
		);
	}

	/**
	 * This error is generated by spring in case a header that is required to process a request is missing
	 */
	@ExceptionHandler(MissingRequestHeaderException.class)
	public final ResponseEntity<?> handleMissingRequestHeaderException (final MissingRequestHeaderException ex) {
		return generateResponse (
			new BadRequestException(ex.getMessage())
		);
	}

	/**
	 * This is the error raised by spring in case the request body is not able to be converted to the specified class
	 */
	@ExceptionHandler(HttpMessageConversionException.class)
	public final ResponseEntity<?> handleHttpMessageConversionException(HttpMessageConversionException ex) {
		logger.error(ex.getMessage());

		return generateResponse (
			new BadRequestException ("Invalid Json")
		);
	}

	/**
	 * This is generated by spring in case any provided type is able to be converted
	 */
	@ExceptionHandler(TypeMismatchException.class)
	public final ResponseEntity<?> handleTypeMismatchException(TypeMismatchException ex) {
		logger.error(ex.getMessage());

		return generateResponse (
			new BadRequestException ("Invalid parameter type")
		);
	}

	/**
	 * This is generated by spring in case any validation checks fail
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BadRequestException bre = new BadRequestException ("Validation errors");

		// build validation info
		bre.validations = ex.getBindingResult().getFieldErrors().stream()
			.map(err -> new ValidationFailure(
				err.getField(),
				err.getRejectedValue().toString(),
				err.getDefaultMessage()
			))
			.distinct()
			.collect(Collectors.toList());

		return generateResponse (bre);
	}

	/**
	 * This is generated by spring in case no supported media type is accepted by sender
	 */
	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	public final ResponseEntity<?> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
		BadRequestException bre = new BadRequestException ("Supported media types : " + ex.getSupportedMediaTypes());

		return generateResponse (bre);
	}

	/**
	 * This is generated by spring in case method argument type mismatch is found
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public final ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		BadRequestException bre = new BadRequestException ("required : " + ex.getRequiredType());
		logger.error(ex.getMessage());

		return generateResponse (bre);
	}

	/**
	 * This is generated in case the user is not able to be authenticated
	 */
	@ExceptionHandler(AuthenticationException.class)
	public final ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
		UnauthorizedException bre = new UnauthorizedException (ex.getMessage());
		logger.error(ex.getMessage());

		return generateResponse (bre);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<?> handleInternalServerException (final Exception ex) {
		logger.error("got exception : " + ex.getClass().getCanonicalName());
		for (var trace : ex.getStackTrace())
			logger.error(trace.toString());

		return generateResponse (
			new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage())
		);
	}

	/**
	 * Wrap the generic exception in a response entity to provide the exception status
	 * as an HttpStatus for the response
	 */
	private ResponseEntity<?> generateResponse (final GenericException ex) {
		return new ResponseEntity<GenericException> (
			ex,
			ex.status
		);
	}
}




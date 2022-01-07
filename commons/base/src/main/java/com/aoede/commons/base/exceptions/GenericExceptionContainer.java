package com.aoede.commons.base.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class GenericExceptionContainer extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private GenericException exception;

	public GenericExceptionContainer (HttpStatus status, String desc) {
		this.exception = new GenericException (status, desc);
	}

	public GenericExceptionContainer (GenericException exception) {
		this.exception = exception;
	}
}




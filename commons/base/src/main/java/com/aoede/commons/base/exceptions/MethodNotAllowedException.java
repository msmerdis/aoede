package com.aoede.commons.base.exceptions;

import org.springframework.http.HttpStatus;

public class MethodNotAllowedException extends GenericException {
	private static final long serialVersionUID = 1L;

	public MethodNotAllowedException(String desc) {
		super(HttpStatus.METHOD_NOT_ALLOWED, desc);
	}
}




package com.aoede.commons.base.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends GenericException {
	private static final long serialVersionUID = 1L;

	public UnauthorizedException(String desc) {
		super(HttpStatus.UNAUTHORIZED, desc);
	}
}




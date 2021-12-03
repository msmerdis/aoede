package com.aoede.commons.base.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends GenericException {
	private static final long serialVersionUID = 1L;

	public BadRequestException(String desc) {
		super(HttpStatus.BAD_REQUEST, desc);
	}
}




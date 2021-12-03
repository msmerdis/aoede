package com.aoede.commons.base.exceptions;

import org.springframework.http.HttpStatus;

public class ConflictException extends GenericException {
	private static final long serialVersionUID = 1L;

	public ConflictException(String desc) {
		super(HttpStatus.CONFLICT, desc);
	}
}




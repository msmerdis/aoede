package com.aoede.commons.base.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends GenericException {
	private static final long serialVersionUID = 1L;

	public NotFoundException(String desc) {
		super(HttpStatus.NOT_FOUND, desc);
	}
}




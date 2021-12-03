package com.aoede.commons.base.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends GenericException {
	private static final long serialVersionUID = 1L;

	public ForbiddenException(String desc) {
		super(HttpStatus.FORBIDDEN, desc);
	}
}




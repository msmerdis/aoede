package com.aoede.commons.base.exceptions;

import org.springframework.http.HttpStatus;

public class NotImplementedException extends GenericException {
	private static final long serialVersionUID = 1L;

	public NotImplementedException(String desc) {
		super(HttpStatus.NOT_IMPLEMENTED, desc);
	}
}




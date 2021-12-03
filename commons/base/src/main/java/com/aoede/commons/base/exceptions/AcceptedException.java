package com.aoede.commons.base.exceptions;

import org.springframework.http.HttpStatus;

public class AcceptedException extends GenericException {
	private static final long serialVersionUID = 1L;

	public AcceptedException() {
		super(HttpStatus.ACCEPTED, null);
	}
}




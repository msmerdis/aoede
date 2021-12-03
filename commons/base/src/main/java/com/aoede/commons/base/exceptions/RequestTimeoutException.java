package com.aoede.commons.base.exceptions;

import org.springframework.http.HttpStatus;

public class RequestTimeoutException extends GenericException {
	private static final long serialVersionUID = 1L;

	public RequestTimeoutException(String desc) {
		super(HttpStatus.REQUEST_TIMEOUT, desc);
	}
}




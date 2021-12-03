package com.aoede.commons.base.exceptions;

import org.springframework.http.HttpStatus;

public class SuccessStatus extends GenericException {
	private static final long serialVersionUID = 1L;

	public SuccessStatus() {
		super(HttpStatus.OK, null);
	}
}




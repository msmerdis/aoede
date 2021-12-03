package com.aoede.commons.base.exceptions;

import org.springframework.http.HttpStatus;

public class NoContentStatus extends GenericException {
	private static final long serialVersionUID = 1L;

	public NoContentStatus() {
		super(HttpStatus.NO_CONTENT, null);
	}
}




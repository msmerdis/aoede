package com.aoede.commons.base.exceptions;

import org.springframework.http.HttpStatus;

public class AcceptedStatus extends GenericException {
	private static final long serialVersionUID = 1L;

	public AcceptedStatus() {
		super(HttpStatus.ACCEPTED, null);
	}
}




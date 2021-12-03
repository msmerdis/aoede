package com.aoede.commons.base.exceptions;

import org.springframework.http.HttpStatus;

public class CreatedStatus extends GenericException {
	private static final long serialVersionUID = 1L;

	public CreatedStatus() {
		super(HttpStatus.CREATED, null);
	}
}




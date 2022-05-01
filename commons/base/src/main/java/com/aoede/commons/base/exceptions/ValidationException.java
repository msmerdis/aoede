package com.aoede.commons.base.exceptions;

import java.util.List;

public class ValidationException extends BadRequestException {
	private static final long serialVersionUID = 1L;
	private static final String error = "Validation errors";

	public ValidationException () {
		super (error);
	}

	public ValidationException (String name, String field, String value, String error) {
		this ();

		super.validations = List.of (
			new ValidationFailure(name, field, value, error)
		);
	}
}




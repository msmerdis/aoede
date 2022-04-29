package com.aoede.commons.base.exceptions;

public class ValidationFailure {
	public String field;
	public String value;
	public String error;

	public ValidationFailure (String field, String value, String error) {
		this.field = field;
		this.value = value;
		this.error = error;
	}

	public ValidationFailure (String field, Object value, String error) {
		this.field = field;
		this.value = value == null ? null : value.toString();
		this.error = error;
	}
}




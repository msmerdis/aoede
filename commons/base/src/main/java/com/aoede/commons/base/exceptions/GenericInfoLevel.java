package com.aoede.commons.base.exceptions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GenericInfoLevel {
	TRACE (32,   "trace"),
	DEBUG (16,   "debug"),
	INFO  ( 8,    "info"),
	WARN  ( 4, "warning"),
	ERROR ( 2,   "error"),
	FATAL ( 1,   "fatal");

	final public int    code;
	final public String text;

	GenericInfoLevel(int code, String text) {
		this.code = code;
		this.text = text;
	}

	@JsonValue
	public String toString () {
		return this.text;
	}

	@JsonCreator
	public static GenericInfoLevel getEnumFromText(String text) {
		for (GenericInfoLevel l : GenericInfoLevel.values()) {
			if (l.text.equals(text)) {
				return l;
			}
		}
		return null;
	}
}




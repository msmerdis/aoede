package com.aoede.commons.base.exceptions;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(
	fieldVisibility = Visibility.NONE,
	setterVisibility = Visibility.NONE,
	getterVisibility = Visibility.NONE,
	isGetterVisibility = Visibility.NONE,
	creatorVisibility = Visibility.NONE
)
public class GenericException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;

	public final HttpStatus status;

	@JsonProperty public final int    code;
	@JsonProperty public final String text;
	@JsonProperty public final String desc;

	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	public List<String> info;

	public GenericException (HttpStatus status, String desc) {
		this.status = status;

		this.code = status.value();
		this.text = status.name();
		this.desc = desc;
		this.info = null;
	}
}




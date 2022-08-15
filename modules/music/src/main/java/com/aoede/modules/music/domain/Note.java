package com.aoede.modules.music.domain;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Note {

	@JsonInclude(Include.NON_EMPTY)
	private List<String> tags;

	@JsonInclude(Include.NON_EMPTY)
	private Map<String,String> flags;

	private int pitch;
	private int value;
}




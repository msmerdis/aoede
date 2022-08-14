package com.aoede.modules.music.domain;

import java.util.HashMap;
import java.util.List;

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
	private HashMap<String,String> flags;

	private Short order;
	private int pitch;
	private Fraction value;
}




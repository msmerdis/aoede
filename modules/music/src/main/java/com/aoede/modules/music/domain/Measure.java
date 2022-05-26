package com.aoede.modules.music.domain;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Measure {
	@NotNull (message = "Measure must define a track id")
	@Positive (message = "Measure must define a positive track id")
	private Short order;

	@Valid
	@NotNull (message = "Measure must define notes")
	private List<Note> notes = new LinkedList<Note>();
}




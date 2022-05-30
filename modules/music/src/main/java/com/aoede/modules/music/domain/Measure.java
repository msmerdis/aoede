package com.aoede.modules.music.domain;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Measure {
	@Valid
	@NotNull (message = "Measure must define notes")
	private List<Note> notes;
}




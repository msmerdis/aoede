package com.aoede.modules.music.domain;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.aoede.modules.music.validation.constraints.ClefConstraint;
import com.aoede.modules.music.validation.constraints.KeySignatureConstraint;
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
@JsonInclude(Include.NON_NULL)
public class Measure {

	@ClefConstraint(message = "Measure defined an invalid clef")
	private String clef = null;

	@Min(value =  32, message = "minimum allowed tempo is 32")
	@Max(value = 512, message = "maximum allowed tempo is 512")
	private Short tempo = null;

	@KeySignatureConstraint(message = "Measure defined an invalid key signature")
	private Short keySignature = null;

	@Valid
	private Fraction timeSignature = null;

	@Valid
	@NotNull (message = "Measure must define notes")
	private List<Note> notes;
}




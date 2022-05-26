package com.aoede.modules.music.domain;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.aoede.modules.music.validation.constraints.ClefConstraint;
import com.aoede.modules.music.validation.constraints.KeySignatureConstraint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Track {
	@NotNull (message = "Track must define a track id")
	@Positive (message = "Track must define a positive track id")
	private Short order;
	private String name;

	@NotNull (message = "Track must define a clef")
	@ClefConstraint(message = "Track defined an invalid clef")
	private String clef;

	@Min(value =  32, message = "minimum allowed tempo is 32")
	@Max(value = 512, message = "maximum allowed tempo is 512")
	private short tempo;

	@NotNull (message = "Section must define a section id")
	@KeySignatureConstraint(message = "Track defined an invalid clef")
	private Short keySignature;

	@Valid
	private Fraction timeSignature;

	@Valid
	@NotNull (message = "Track must define measures")
	private List<Measure> measures = new LinkedList<Measure>();
}




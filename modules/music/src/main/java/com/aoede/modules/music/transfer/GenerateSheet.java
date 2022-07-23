package com.aoede.modules.music.transfer;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.aoede.modules.music.domain.TimeSignature;
import com.aoede.modules.music.validation.constraints.ClefConstraint;
import com.aoede.modules.music.validation.constraints.KeySignatureConstraint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class GenerateSheet {
	@NotEmpty (message = "Sheet must have a name")
	private String name;

	@NotNull (message = "Sheet must have a clef")
	@ClefConstraint(message = "Sheet defined an invalid clef")
	private String clef;

	@Min(value =  32, message = "minimum allowed tempo is 32")
	@Max(value = 512, message = "maximum allowed tempo is 512")
	private short tempo;

	@NotNull (message = "Sheet must have a keySignature")
	@KeySignatureConstraint(message = "Sheet defined an invalid key signature")
	private Short keySignature;

	@Valid
	@NotNull (message = "Sheet must have a time signature")
	private TimeSignature timeSignature;
}




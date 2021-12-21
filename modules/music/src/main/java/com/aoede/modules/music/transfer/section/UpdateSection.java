package com.aoede.modules.music.transfer.section;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.apache.commons.lang3.math.Fraction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UpdateSection {
	@Positive(message = "Section must define a positive tempo")
	@Min(value =  32, message = "minimum allowed tempo is 32")
	@Max(value = 512, message = "maximum allowed tempo is 512")
	private short tempo;

	@NotEmpty (message = "Section must define a key signature")
	private String keySignature;

	@NotNull(message = "Section must define a time signature")
	private Fraction timeSignature;
}




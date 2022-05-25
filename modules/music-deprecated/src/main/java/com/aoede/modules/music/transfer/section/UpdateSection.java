package com.aoede.modules.music.transfer.section;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.aoede.modules.music.transfer.Fraction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UpdateSection {
	@Min(value =  32, message = "minimum allowed tempo is 32")
	@Max(value = 512, message = "maximum allowed tempo is 512")
	private short tempo;

	private short keySignature;

	@Valid
	@NotNull(message = "Section must define a time signature")
	private Fraction timeSignature;
}




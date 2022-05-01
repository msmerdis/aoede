package com.aoede.modules.music.transfer.note;

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
public class UpdateNote {
	@Min(value =  -1, message = "minimum allowed note value is -1")
	@Max(value = 127, message = "maximum allowed note value is 127")
	private short note;

	@Valid
	@NotNull(message = "note must be a value")
	private Fraction value;
}




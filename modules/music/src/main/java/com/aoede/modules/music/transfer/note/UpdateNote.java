package com.aoede.modules.music.transfer.note;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class UpdateNote {
	@Min(value =  -1, message = "minimum allowed note value is -1")
	@Max(value = 127, message = "maximum allowed note value is 127")
	private short note;

	@Positive(message = "note must be a value")
	private Fraction value;
}




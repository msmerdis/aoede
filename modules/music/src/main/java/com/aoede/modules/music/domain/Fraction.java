package com.aoede.modules.music.domain;

import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Fraction {
	@Positive(message = "numerator must be a positive value")
	private int numerator;

	@Positive(message = "denominator must be a positive value")
	private int denominator;
}




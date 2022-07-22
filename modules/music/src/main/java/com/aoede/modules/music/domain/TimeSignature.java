package com.aoede.modules.music.domain;

import java.util.List;

import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.commons.base.entity.AbstractEntity;
import com.aoede.modules.music.validation.constraints.BeatsConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@BeatsConstraint(message = "Time signature defined invalid beats")
public class TimeSignature extends Fraction implements AbstractDomain<Fraction>, AbstractEntity<Fraction> {
	@JsonInclude(Include.NON_NULL)
	private List<Integer> beats;

	public TimeSignature (int numerator, int denominator, List<Integer> beats) {
		super(numerator, denominator);

		this.beats = beats;
	}

	public TimeSignature (int numerator, int denominator) {
		this (numerator, denominator, null);
	}

	@Override
	@JsonIgnore
	public Fraction getId() {
		return this;
	}

	@Override
	@JsonIgnore
	public void setId(Fraction id) {
		super.setNumerator(id.getNumerator());
		super.setDenominator(id.getDenominator());
	}
}




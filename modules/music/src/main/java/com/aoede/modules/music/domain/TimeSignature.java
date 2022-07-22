package com.aoede.modules.music.domain;

import java.util.List;

import com.aoede.modules.music.validation.constraints.BeatsConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@BeatsConstraint(message = "Time signature defined invalid beats")
public class TimeSignature extends Fraction {
	@JsonInclude(Include.NON_NULL)
	private List<Integer> beats;
}




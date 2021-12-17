package com.aoede.modules.music.domain;

import java.util.List;

import org.apache.commons.lang3.math.Fraction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Section {
	private short tempo;
	private String keySignature;
	private Fraction timeSignature;
	private List<Measure> measures;
}




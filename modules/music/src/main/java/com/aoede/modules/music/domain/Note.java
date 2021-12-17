package com.aoede.modules.music.domain;

import org.apache.commons.lang3.math.Fraction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Note {
	private int note [];
	private Fraction value;
}




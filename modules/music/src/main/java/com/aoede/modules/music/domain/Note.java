package com.aoede.modules.music.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Note {
	private Short order;
	private int pitch;
	private Fraction value;
}




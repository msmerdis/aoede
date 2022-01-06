package com.aoede.modules.music.domain;

import java.util.List;

import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.modules.music.transfer.Fraction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Section implements AbstractDomain<Long> {
	private Long id;
	private short tempo;
	private KeySignature keySignature;
	private Fraction timeSignature;
	private Track track;
	private List<Measure> measures;
}




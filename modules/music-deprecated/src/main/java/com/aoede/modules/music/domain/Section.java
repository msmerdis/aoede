package com.aoede.modules.music.domain;

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
public class Section implements AbstractDomain<SectionKey> {
	private SectionKey id;
	private short tempo;
	private KeySignature keySignature;
	private Fraction timeSignature;
}




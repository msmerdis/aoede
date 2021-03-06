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
public class Note implements AbstractDomain<NoteKey> {
	private NoteKey id;
	private int note;
	private Fraction value;
}




package com.aoede.modules.music.transfer.note;

import com.aoede.commons.base.transfer.AbstractResponse;
import com.aoede.modules.music.transfer.Fraction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SimpleNoteResponse implements AbstractResponse <Long> {
	private Long noteId;
	private int note;
	private Fraction value;
}




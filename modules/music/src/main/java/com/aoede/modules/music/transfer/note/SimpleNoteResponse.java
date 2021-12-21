package com.aoede.modules.music.transfer.note;

import com.aoede.modules.music.domain.Clef;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SimpleNoteResponse {
	private Long noteId;
	private Clef clef;
}




package com.aoede.modules.music.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
@Embeddable
public class NoteId extends MeasureId {
	@Column(name = "noteId")
	private short noteId;
}




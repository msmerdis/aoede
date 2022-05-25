package com.aoede.modules.music.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Embeddable
@MappedSuperclass
public class NoteId extends MeasureId {
	private static final long serialVersionUID = 1L;

	@Column(name = "noteId", nullable = false)
	private short noteId;

	public NoteId (Long sheetId, short trackId, short sectionId, short measureId, short noteId) {
		super(sheetId, trackId, sectionId, measureId);

		this.noteId = noteId;
	}
}




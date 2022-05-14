package com.aoede.modules.music.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.aoede.commons.base.entity.AbstractEntity;
import com.aoede.commons.base.entity.AbstractJpaEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
	name = "tnote",
	indexes = {
		@Index(columnList = "sheetId, trackId, sectionId, measureId, noteId", unique = true)
	}
)
public class NoteEntity extends AbstractJpaEntity<NoteId> implements AbstractEntity<NoteId>, Sheetable {
	@EmbeddedId
	private NoteId id;

	@Column(nullable = false)
	private int note;

	@Column(nullable = false)
	private int valueNum;

	@Column(nullable = false)
	private int valueDen;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sheetId", referencedColumnName = "sheetId", nullable = false, insertable = false, updatable = false)
	private SheetEntity sheet;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "sheetId", referencedColumnName = "sheetId", nullable = false, insertable = false, updatable = false),
		@JoinColumn(name = "trackId", referencedColumnName = "trackId", nullable = false, insertable = false, updatable = false)
	})
	private TrackEntity track;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "sheetId", referencedColumnName = "sheetId", nullable = false, insertable = false, updatable = false),
		@JoinColumn(name = "trackId", referencedColumnName = "trackId", nullable = false, insertable = false, updatable = false),
		@JoinColumn(name = "sectionId", referencedColumnName = "sectionId", nullable = false, insertable = false, updatable = false)
	})
	private SectionEntity section;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "sheetId", referencedColumnName = "sheetId", nullable = false, insertable = false, updatable = false),
		@JoinColumn(name = "trackId", referencedColumnName = "trackId", nullable = false, insertable = false, updatable = false),
		@JoinColumn(name = "sectionId", referencedColumnName = "sectionId", nullable = false, insertable = false, updatable = false),
		@JoinColumn(name = "measureId", referencedColumnName = "measureId", nullable = false, insertable = false, updatable = false)
	})
	private MeasureEntity measure;
}




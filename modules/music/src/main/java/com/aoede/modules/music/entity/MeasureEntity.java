package com.aoede.modules.music.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.aoede.commons.base.entity.AbstractEntity;
import com.aoede.commons.base.entity.AbstractJpaEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
	name = "tmeasure",
	indexes = {
		@Index(columnList = "sheetId, trackId, sectionId, measureId", unique = true)
	}
)
public class MeasureEntity extends AbstractJpaEntity<MeasureId> implements AbstractEntity<MeasureId> {
	@EmbeddedId
	private MeasureId id;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId("sheedId")
	@JoinColumn(name = "sheetId", referencedColumnName = "sheetId", nullable = false)
	private SheetEntity sheet;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId("sheetId, trackId")
	@JoinColumns({
		@JoinColumn(name = "sheetId", referencedColumnName = "sheetId", nullable = false, insertable = false, updatable = false),
		@JoinColumn(name = "trackId", referencedColumnName = "trackId", nullable = false, insertable = false, updatable = false)
	})
	private TrackEntity track;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId("sheetId, trackId, sectionId")
	@JoinColumns({
		@JoinColumn(name = "sheetId", referencedColumnName = "sheetId", nullable = false, insertable = false, updatable = false),
		@JoinColumn(name = "trackId", referencedColumnName = "trackId", nullable = false, insertable = false, updatable = false),
		@JoinColumn(name = "sectionId", referencedColumnName = "sectionId", nullable = false, insertable = false, updatable = false)
	})
	private SectionEntity section;

	@Fetch(FetchMode.SELECT)
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "measure")
	private Set<NoteEntity> notes = new HashSet<NoteEntity> ();
}




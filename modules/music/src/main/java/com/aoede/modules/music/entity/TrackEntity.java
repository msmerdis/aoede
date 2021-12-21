package com.aoede.modules.music.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.aoede.commons.base.entity.AbstractEntity;
import com.aoede.commons.base.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
	name = "ttrack",
	indexes = {
		@Index(columnList = "id", unique = true),
		@Index(columnList = "id, sheetId")
	}
)
@SequenceGenerator(name = "trackIdGenerator", sequenceName = "TRACK_SEQ", initialValue = 1, allocationSize = 1)
public class TrackEntity extends BaseEntity<Long> implements AbstractEntity<Long> {
	@Column(length = 32, nullable = false)
	private String clef;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sheetId", referencedColumnName = "Id", nullable = false)
	private SheetEntity sheet;

	@Fetch(FetchMode.SELECT)
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "track")
	private Set<SectionEntity> sections = new HashSet<SectionEntity> ();
}




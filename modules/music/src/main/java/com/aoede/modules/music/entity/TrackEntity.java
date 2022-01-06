package com.aoede.modules.music.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
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
	name = "ttrack",
	indexes = {
		@Index(columnList = "trackId", unique = true),
		@Index(columnList = "trackId, sheetId")
	}
)
@SequenceGenerator(name = "trackIdGenerator", sequenceName = "TRACK_SEQ", initialValue = 1, allocationSize = 1)
public class TrackEntity extends AbstractJpaEntity<Long> implements AbstractEntity<Long> {
	@Id
	@Column(name = "trackId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trackIdGenerator")
	private Long id;

	@Column(length = 32, nullable = false)
	private String clef;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sheetId", referencedColumnName = "sheetId", nullable = false)
	private SheetEntity sheet;

	@Fetch(FetchMode.SELECT)
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "track")
	private Set<SectionEntity> sections = new HashSet<SectionEntity> ();
}




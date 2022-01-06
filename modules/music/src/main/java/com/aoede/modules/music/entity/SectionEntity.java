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
	name = "tsection",
	indexes = {
		@Index(columnList = "sectionId", unique = true),
		@Index(columnList = "sectionId, trackId")
	}
)
@SequenceGenerator(name = "sectionIdGenerator", sequenceName = "SECTION_SEQ", initialValue = 1, allocationSize = 1)
public class SectionEntity extends AbstractJpaEntity<Long> implements AbstractEntity<Long> {
	@Id
	@Column(name = "sectionId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sectionIdGenerator")
	private Long id;

	@Column(nullable = false)
	private short tempo;

	@Column(length = 32, nullable = false)
	private String keySignature;

	@Column(nullable = false)
	private int timeSignatureNumerator;

	@Column(nullable = false)
	private int timeSignatureDenominator;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trackId", referencedColumnName = "trackId", nullable = false)
	private TrackEntity track;

	@Fetch(FetchMode.SELECT)
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "section")
	private Set<MeasureEntity> measures = new HashSet<MeasureEntity> ();
}




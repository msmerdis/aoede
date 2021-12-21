package com.aoede.modules.music.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.aoede.commons.base.entity.AbstractEntity;
import com.aoede.commons.base.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
	name = "tsection",
	indexes = {
		@Index(columnList = "id", unique = true),
		@Index(columnList = "id, trackId")
	}
)
@SequenceGenerator(name = "sectionIdGenerator", sequenceName = "SECTION_SEQ", initialValue = 1, allocationSize = 1)
public class SectionEntity extends BaseEntity<Long> implements AbstractEntity<Long> {
	@Column(nullable = false)
	private short tempo;

	@Column(length = 32, nullable = false)
	private String keySignature;

	@Column(nullable = false)
	private int timeSignatureNumerator;

	@Column(nullable = false)
	private int timeSignatureDenominator;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trackId", referencedColumnName = "Id", nullable = false)
	private TrackEntity track;
}




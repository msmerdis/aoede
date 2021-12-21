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
	name = "tnote",
	indexes = {
		@Index(columnList = "id", unique = true),
		@Index(columnList = "id, measureId")
	}
)
@SequenceGenerator(name = "noteIdGenerator", sequenceName = "NOTE_SEQ", initialValue = 1, allocationSize = 1)
public class NoteEntity extends BaseEntity<Long> implements AbstractEntity<Long> {
	@Column(nullable = false)
	private int note;

	@Column(nullable = false)
	private int valueNum;

	@Column(nullable = false)
	private int valueDen;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "measureId", referencedColumnName = "Id", nullable = false)
	private MeasureEntity measure;
}




package com.aoede.modules.music.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
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
		@Index(columnList = "noteId", unique = true),
		@Index(columnList = "noteId, measureId")
	}
)
@SequenceGenerator(name = "noteIdGenerator", sequenceName = "NOTE_SEQ", initialValue = 1, allocationSize = 1)
public class NoteEntity extends AbstractJpaEntity<Long> implements AbstractEntity<Long> {
	@Id
	@Column(name = "noteId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "noteIdGenerator")
	private Long id;

	@Column(nullable = false)
	private int note;

	@Column(nullable = false)
	private int valueNum;

	@Column(nullable = false)
	private int valueDen;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "measureId", referencedColumnName = "measureId", nullable = false)
	private MeasureEntity measure;
}




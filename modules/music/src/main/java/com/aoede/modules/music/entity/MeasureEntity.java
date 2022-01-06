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
	name = "tmeasure",
	indexes = {
		@Index(columnList = "measureId", unique = true),
		@Index(columnList = "measureId, sectionId")
	}
)
@SequenceGenerator(name = "measureIdGenerator", sequenceName = "MEASURE_SEQ", initialValue = 1, allocationSize = 1)
public class MeasureEntity extends AbstractJpaEntity<Long> implements AbstractEntity<Long> {
	@Id
	@Column(name = "measureId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "measureIdGenerator")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sectionId", referencedColumnName = "sectionId", nullable = false)
	private SectionEntity section;

	@Fetch(FetchMode.SELECT)
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "measure")
	private Set<NoteEntity> notes = new HashSet<NoteEntity> ();
}




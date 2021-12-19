package com.aoede.modules.music.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
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
	name = "tsheet",
	indexes = {
		@Index(columnList = "id", unique = true)
	}
)
@SequenceGenerator(name = "idGenerator", sequenceName = "SHEET_SEQ", initialValue = 1, allocationSize = 1)
public class SheetEntity extends BaseEntity<Long> implements AbstractEntity<Long> {
	@Column
	private String name;

	@Fetch(FetchMode.SELECT)
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "sheet")
	private Set<TrackEntity> tracks = new HashSet<TrackEntity> ();
}




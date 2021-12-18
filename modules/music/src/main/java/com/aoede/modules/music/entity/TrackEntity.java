package com.aoede.modules.music.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
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
	name = "ttrack",
	indexes = {
		@Index(columnList = "id", unique = true)
	}
)
@SequenceGenerator(name = "idGenerator", sequenceName = "TRACK_SEQ", initialValue = 1, allocationSize = 1)
public class TrackEntity extends BaseEntity<Long> implements AbstractEntity<Long> {
	@Column
	private String clef;
}




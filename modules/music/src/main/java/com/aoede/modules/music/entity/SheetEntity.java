package com.aoede.modules.music.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.aoede.commons.base.entity.AbstractEntity;
import com.aoede.commons.base.entity.AbstractJpaEntity;
import com.aoede.modules.music.domain.Track;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
	name = "tsheet",
	indexes = {
		@Index(columnList = "sheetId", unique = true)
	}
)
@SequenceGenerator(name = "sheetIdGenerator", sequenceName = "SHEET_SEQ", initialValue = 1, allocationSize = 1)
public class SheetEntity extends AbstractJpaEntity<Long> implements AbstractEntity<Long> {
	@Id
	@Column(name = "sheetId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sheetIdGenerator")
	private Long id;

	@Column(nullable = false)
	private Long userId;

	@Column(length = 512, nullable = false)
	private String name;

	@Convert(converter = TrackListConverter.class)
	@Column(nullable = false, columnDefinition = "text")
	private List<Track> tracks;
}




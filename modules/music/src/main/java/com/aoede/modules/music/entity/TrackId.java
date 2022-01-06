package com.aoede.modules.music.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
@MappedSuperclass
public class TrackId {
	@Column(name = "sheetId")
	private Long sheetId;

	@Column(name = "trackId")
	private short trackId;
}




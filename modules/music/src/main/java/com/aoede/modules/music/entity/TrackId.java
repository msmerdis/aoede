package com.aoede.modules.music.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
@MappedSuperclass
public class TrackId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "sheetId", nullable = false)
	private Long sheetId;

	@Column(name = "trackId", nullable = false)
	private short trackId;
}




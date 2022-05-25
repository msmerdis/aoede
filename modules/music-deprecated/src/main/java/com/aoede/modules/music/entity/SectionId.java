package com.aoede.modules.music.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Embeddable
@MappedSuperclass
public class SectionId extends TrackId {
	private static final long serialVersionUID = 1L;

	@Column(name = "sectionId", nullable = false)
	private short sectionId;

	public SectionId (Long sheetId, short trackId, short sectionId) {
		super(sheetId, trackId);

		this.sectionId = sectionId;
	}
}




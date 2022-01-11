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
public class MeasureId extends SectionId {
	private static final long serialVersionUID = 1L;

	@Column(name = "measureId", nullable = false)
	private short measureId;

	public MeasureId (Long sheetId, short trackId, short sectionId, short measureId) {
		super(sheetId, trackId, sectionId);

		this.measureId = measureId;
	}
}




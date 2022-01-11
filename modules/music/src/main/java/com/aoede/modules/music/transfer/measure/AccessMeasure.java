package com.aoede.modules.music.transfer.measure;

import com.aoede.modules.music.transfer.section.AccessSection;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AccessMeasure extends AccessSection {
	private Short measureId;

	public AccessMeasure (Long sheetId, Short trackId, Short sectionId, Short measureId) {
		super (sheetId, trackId, sectionId);

		this.measureId = measureId;
	}
}




package com.aoede.modules.music.transfer.note;

import com.aoede.modules.music.transfer.measure.AccessMeasure;

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
public class AccessNote extends AccessMeasure {
	private Short noteId;

	public AccessNote (Long sheetId, Short trackId, Short sectionId, Short measureId, Short noteId) {
		super (sheetId, trackId, sectionId, measureId);

		this.noteId = noteId;
	}
}




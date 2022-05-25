package com.aoede.modules.music.transfer.section;

import com.aoede.modules.music.transfer.track.AccessTrack;

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
public class AccessSection extends AccessTrack {
	private Short sectionId;

	public AccessSection (Long sheetId, Short trackId, Short sectionId) {
		super (sheetId, trackId);

		this.sectionId = sectionId;
	}
}




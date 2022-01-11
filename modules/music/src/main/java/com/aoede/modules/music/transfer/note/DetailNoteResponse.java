package com.aoede.modules.music.transfer.note;

import com.aoede.modules.music.transfer.measure.AccessMeasure;
import com.aoede.modules.music.transfer.section.AccessSection;
import com.aoede.modules.music.transfer.track.AccessTrack;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DetailNoteResponse extends SimpleNoteResponse {
	private Long sheetId;
	private AccessTrack trackId;
	private AccessSection sectionId;
	private AccessMeasure measureId;
}




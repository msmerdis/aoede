package com.aoede.modules.music.transfer.note;

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
	private Long trackId;
	private Long sectionId;
	private Long measureId;
}




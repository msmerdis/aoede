package com.aoede.modules.music.transfer.measure;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DetailMeasureResponse extends SimpleMeasureResponse {
	private Long sheetId;
	private Long trackId;
	private Long sectionId;
}




package com.aoede.modules.music.transfer.track;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DetailTrackResponse extends SimpleTrackResponse {
	private Long sheetId;
}




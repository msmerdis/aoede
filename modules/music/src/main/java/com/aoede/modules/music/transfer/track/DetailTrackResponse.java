package com.aoede.modules.music.transfer.track;

import java.util.List;

import com.aoede.modules.music.transfer.section.SimpleSectionResponse;

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
	public List<SimpleSectionResponse> sections = null;
}




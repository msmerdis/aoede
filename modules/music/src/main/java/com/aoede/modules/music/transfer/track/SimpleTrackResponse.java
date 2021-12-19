package com.aoede.modules.music.transfer.track;

import com.aoede.modules.music.domain.Clef;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SimpleTrackResponse {
	private Long trackId;
	private Clef clef;
}




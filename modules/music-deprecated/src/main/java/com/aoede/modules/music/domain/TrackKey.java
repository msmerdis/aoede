package com.aoede.modules.music.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TrackKey {
	private Long  sheetId;
	private Short trackId;
}




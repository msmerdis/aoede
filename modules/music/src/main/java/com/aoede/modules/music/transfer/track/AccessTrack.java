package com.aoede.modules.music.transfer.track;

import com.aoede.commons.base.transfer.CompositeKey;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccessTrack implements CompositeKey {
	private Long  sheetId;
	private Short trackId;
}




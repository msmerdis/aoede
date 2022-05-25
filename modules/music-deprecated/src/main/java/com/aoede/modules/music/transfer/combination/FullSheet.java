package com.aoede.modules.music.transfer.combination;

import java.util.List;

import com.aoede.modules.music.transfer.sheet.DetailSheetResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FullSheet extends DetailSheetResponse {
	private List<FullTrack> tracks;
}




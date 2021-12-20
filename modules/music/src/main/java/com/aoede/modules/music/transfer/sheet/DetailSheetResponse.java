package com.aoede.modules.music.transfer.sheet;

import java.util.List;

import com.aoede.modules.music.transfer.track.SimpleTrackResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class DetailSheetResponse extends SimpleSheetResponse {
	public List<SimpleTrackResponse> tracks = null;
}




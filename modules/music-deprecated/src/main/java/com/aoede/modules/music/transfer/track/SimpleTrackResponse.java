package com.aoede.modules.music.transfer.track;

import com.aoede.commons.base.transfer.AbstractCompositeResponse;
import com.aoede.modules.music.domain.Clef;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SimpleTrackResponse implements AbstractCompositeResponse <AccessTrack> {
	private AccessTrack id;
	private Clef clef;
}




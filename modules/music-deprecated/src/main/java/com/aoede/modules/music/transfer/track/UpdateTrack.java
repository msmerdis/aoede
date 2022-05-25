package com.aoede.modules.music.transfer.track;

import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UpdateTrack {
	@NotNull (message = "Track must define a clef")
	private String clef;
}




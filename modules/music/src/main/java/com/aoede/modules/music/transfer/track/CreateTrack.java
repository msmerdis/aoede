package com.aoede.modules.music.transfer.track;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CreateTrack extends UpdateTrack {
	@NotNull (message = "Track must define a sheet id")
	@Positive (message = "Track must define a positive sheet id")
	private Long sheetId;
}




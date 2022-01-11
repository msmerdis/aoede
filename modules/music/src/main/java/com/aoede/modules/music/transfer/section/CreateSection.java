package com.aoede.modules.music.transfer.section;

import javax.validation.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CreateSection extends UpdateSection {
	@NotEmpty (message = "Section must define a track id")
	private String trackId;
}




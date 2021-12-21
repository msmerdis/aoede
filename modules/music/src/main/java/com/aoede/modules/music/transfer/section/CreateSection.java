package com.aoede.modules.music.transfer.section;

import javax.validation.constraints.Positive;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CreateSection extends UpdateSection {
	@Positive (message = "Section must define a positive track id")
	private Long trackId;
}




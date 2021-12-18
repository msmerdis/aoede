package com.aoede.modules.music.transfer.sheet;

import javax.validation.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UpdateSheet {
	@NotEmpty (message = "Sheet must define a name")
	private String name;
}




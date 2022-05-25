package com.aoede.modules.music.transfer.note;

import javax.validation.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CreateNote extends UpdateNote {
	@NotEmpty (message = "Note must define a measure id")
	private String measureId;
}




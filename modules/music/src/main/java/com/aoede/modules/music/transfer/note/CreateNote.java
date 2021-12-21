package com.aoede.modules.music.transfer.note;

import javax.validation.constraints.Positive;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CreateNote extends UpdateNote {
	@Positive (message = "Note must define a positive measure id")
	private Long measureId;
}




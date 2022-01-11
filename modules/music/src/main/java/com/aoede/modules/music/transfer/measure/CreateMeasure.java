package com.aoede.modules.music.transfer.measure;

import javax.validation.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMeasure extends UpdateMeasure {
	@NotEmpty (message = "Measure must define a section id")
	private String sectionId;
}




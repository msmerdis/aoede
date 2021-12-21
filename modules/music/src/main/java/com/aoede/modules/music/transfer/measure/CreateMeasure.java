package com.aoede.modules.music.transfer.measure;

import javax.validation.constraints.Positive;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMeasure extends UpdateMeasure {
	@Positive (message = "Measure must define a positive section id")
	private Long sectionId;
}




package com.aoede.modules.music.transfer.measure;

import com.aoede.commons.base.transfer.AbstractCompositeResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SimpleMeasureResponse implements AbstractCompositeResponse <AccessMeasure> {
	private AccessMeasure id;
}




package com.aoede.modules.music.transfer.combination;

import java.util.List;

import com.aoede.modules.music.transfer.measure.DetailMeasureResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FullMeasure extends DetailMeasureResponse {
	private List<FullNote> notes;
}




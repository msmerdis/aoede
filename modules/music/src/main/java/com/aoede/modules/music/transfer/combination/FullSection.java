package com.aoede.modules.music.transfer.combination;

import java.util.List;

import com.aoede.modules.music.transfer.section.DetailSectionResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FullSection extends DetailSectionResponse {
	private List<FullMeasure> measures;
}




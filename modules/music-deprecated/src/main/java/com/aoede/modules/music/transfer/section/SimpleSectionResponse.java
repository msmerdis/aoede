package com.aoede.modules.music.transfer.section;

import com.aoede.commons.base.transfer.AbstractCompositeResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SimpleSectionResponse extends UpdateSection implements AbstractCompositeResponse <AccessSection> {
	private AccessSection id;
}




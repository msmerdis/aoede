package com.aoede.commons.base.compositekey;

import com.aoede.commons.base.transfer.AbstractCompositeResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class CompositeKeySimpleResponse implements AbstractCompositeResponse <CompositeKeyAccessData> {
	private CompositeKeyAccessData id;
	private Integer parentId;
	private Integer childId;
	private String value;
}




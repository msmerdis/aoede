package com.aoede.commons.base.simplekey;

import com.aoede.commons.base.transfer.AbstractResponse;

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
public class SimpleKeySimpleResponse  implements AbstractResponse <Integer> {
	private Integer id;
	private String  value;
}




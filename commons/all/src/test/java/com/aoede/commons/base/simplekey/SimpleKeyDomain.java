package com.aoede.commons.base.simplekey;

import com.aoede.commons.base.domain.AbstractDomain;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class SimpleKeyDomain implements AbstractDomain<Integer> {
	private Integer id;
	private String  value;
}




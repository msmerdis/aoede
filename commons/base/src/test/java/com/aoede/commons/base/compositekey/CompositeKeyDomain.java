package com.aoede.commons.base.compositekey;

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
public class CompositeKeyDomain implements AbstractDomain<CompositeKeyDomainKey> {
	private CompositeKeyDomainKey id;
	private String value;
}




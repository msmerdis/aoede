package com.aoede.modules.music.domain;

import com.aoede.commons.base.domain.AbstractDomain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Sheet implements AbstractDomain<Long> {
	private Long id;
	private String name;
}




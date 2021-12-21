package com.aoede.modules.music.domain;

import org.apache.commons.lang3.math.Fraction;

import com.aoede.commons.base.domain.AbstractDomain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Note extends AbstractDomain<Long> {
	private int note;
	private Fraction value;
	private Measure measure;
}




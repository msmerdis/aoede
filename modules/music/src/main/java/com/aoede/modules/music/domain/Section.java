package com.aoede.modules.music.domain;

import java.util.List;

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
public class Section extends AbstractDomain<Long> {
	private short tempo;
	private String keySignature;
	private Fraction timeSignature;
	private List<Measure> measures;
}




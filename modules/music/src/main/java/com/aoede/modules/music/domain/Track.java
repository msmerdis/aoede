package com.aoede.modules.music.domain;

import java.util.List;

import com.aoede.commons.base.domain.AbstractDomain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Track extends AbstractDomain<Long> {
	private Clef clef;
	private List<Section> sections;
}




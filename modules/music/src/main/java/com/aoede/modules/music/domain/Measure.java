package com.aoede.modules.music.domain;

import java.util.List;

import com.aoede.commons.base.domain.AbstractDomain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Measure extends AbstractDomain<Long> {
	private Section section;
	private List<Note> notes;
}




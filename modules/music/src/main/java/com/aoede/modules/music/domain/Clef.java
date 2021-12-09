package com.aoede.modules.music.domain;

import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.commons.base.entity.AbstractEntity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Clef extends AbstractDomain<String> implements AbstractEntity<String> {
	private char type; // corresponds to the sign the clef should be using
	private int  note; // the note the clef denotes (midi format)
	private int  spos; // denotes the position on the stave the clef should be placed
}




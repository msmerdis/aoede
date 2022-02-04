package com.aoede.commons.base.compositekey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class CompositeKeyEntityKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "parentId")
	private Integer parentId;

	@Column(name = "childId")
	private Integer childId;
}




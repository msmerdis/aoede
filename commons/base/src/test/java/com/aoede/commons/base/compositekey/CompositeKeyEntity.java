package com.aoede.commons.base.compositekey;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.aoede.commons.base.entity.AbstractEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "tcompositekeyentity")
public class CompositeKeyEntity implements AbstractEntity<CompositeKeyEntityKey> {
	@EmbeddedId
	private CompositeKeyEntityKey id;

	@Column(nullable = false, length = 64, unique = true)
	private String  value;
}




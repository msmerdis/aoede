package com.aoede.commons.base.simplekey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.aoede.commons.base.entity.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tsimplekeyentity")
@SequenceGenerator(name = "simpleKeyGenerator", sequenceName = "SIMPLE_KEY_SEQ", initialValue = 1, allocationSize = 1)
public class SimpleKeyEntity implements AbstractEntity<Integer> {
	@Id
	@Column(name = "noteId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "simpleKeyGenerator")
	private Integer id;

	@Column(nullable = false, length = 64, unique = true)
	private String  value;
}




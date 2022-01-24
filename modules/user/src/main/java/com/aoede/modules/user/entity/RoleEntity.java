package com.aoede.modules.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.aoede.commons.base.entity.AbstractEntity;
import com.aoede.commons.base.entity.AbstractJpaEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
	name = "trole",
	indexes = {
		@Index(columnList = "roleId", unique = true),
		@Index(columnList = "role",   unique = true)
	}
)
@SequenceGenerator(name = "roleIdGenerator", sequenceName = "ROLE_SEQ", initialValue = 1, allocationSize = 1)
public class RoleEntity extends AbstractJpaEntity<Integer> implements AbstractEntity<Integer> {
	@Id
	@Column(name = "roleId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleIdGenerator")
	private Integer id;

	@Column(length = 16, nullable = false)
	private String role;

	@Column(length = 512, nullable = true)
	private String desc;
}




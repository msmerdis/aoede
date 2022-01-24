package com.aoede.modules.user.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.aoede.commons.base.entity.AbstractEntity;
import com.aoede.commons.base.entity.AbstractJpaEntity;
import com.aoede.modules.user.converter.UserStatusConverter;
import com.aoede.modules.user.transfer.user.UserStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
	name = "tuser",
	indexes = {
		@Index(columnList =  "userId",  unique = true),
		@Index(columnList = "username", unique = true)
	}
)
@SequenceGenerator(name = "userIdGenerator", sequenceName = "USER_SEQ", initialValue = 1, allocationSize = 1)
public class UserEntity extends AbstractJpaEntity<Long> implements AbstractEntity<Long> {
	@Id
	@Column(name = "userId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userIdGenerator")
	private Long id;

	@Convert(converter = UserStatusConverter.class)
	@Column(length = 1, nullable = false)
	private UserStatus status;

	@Column(length = 512, nullable = false)
	private String username;

	@Column(length = 40, nullable = false)
	private String password;

	@Column(length = 40, nullable = false)
	private String passsalt;

	@Fetch(FetchMode.SELECT)
	@JoinTable(
		name = "tuserroles",
		joinColumns = @JoinColumn(name="userId"),
		inverseJoinColumns = @JoinColumn(name="roleId")
	)
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<RoleEntity> roles = new HashSet<RoleEntity> ();
}




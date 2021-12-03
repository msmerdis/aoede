package com.aoede.commons.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.ToString;

/**
 * This class holds all common attributes an entity should have
 */
@Getter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractJpaEntity<Key> extends AbstractEntity<Key> {
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	@CreatedDate
	protected Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	@LastModifiedDate
	protected Date updatedDate;
}




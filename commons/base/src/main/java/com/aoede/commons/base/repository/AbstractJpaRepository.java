package com.aoede.commons.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.aoede.commons.base.entity.AbstractEntity;

@NoRepositoryBean
public interface AbstractJpaRepository<
	EntityId, Entity extends AbstractEntity<EntityId>
> extends AbstractRepository<EntityId, Entity>, JpaRepository<Entity, EntityId> {
}




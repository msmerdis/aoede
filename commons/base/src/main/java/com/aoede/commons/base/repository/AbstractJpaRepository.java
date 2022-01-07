package com.aoede.commons.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.aoede.commons.base.entity.AbstractEntity;

@NoRepositoryBean
public interface AbstractJpaRepository<
	EntityKey, Entity extends AbstractEntity<EntityKey>
> extends AbstractRepository<EntityKey, Entity>, JpaRepository<Entity, EntityKey> {
}




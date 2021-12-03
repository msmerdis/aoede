package com.aoede.commons.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.aoede.commons.base.entity.AbstractEntity;

@NoRepositoryBean
public interface AbstractJpaRepository<Key, Entity extends AbstractEntity<Key>> extends AbstractRepository<Key, Entity>, JpaRepository<Entity, Key> {
}




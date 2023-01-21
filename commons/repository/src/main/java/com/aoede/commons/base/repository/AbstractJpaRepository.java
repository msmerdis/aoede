package com.aoede.commons.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.aoede.commons.base.entity.AbstractEntity;

// TODO: remove
@NoRepositoryBean
public interface AbstractJpaRepository<K, E extends AbstractEntity<K>> extends AbstractRepository<K, E>, JpaRepository<E, K> {
}




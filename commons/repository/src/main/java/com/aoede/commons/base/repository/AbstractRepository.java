package com.aoede.commons.base.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;

import com.aoede.commons.base.entity.AbstractEntity;

/**
 * Definition of basic CRUD operations for entities
 */
@NoRepositoryBean
public interface AbstractRepository<K, E extends AbstractEntity<K>> {
	public List<E> findAll();

	public Optional<E> findById(K id);

	public void deleteById(K id);

	public boolean existsById(K id);

	public E save(E entity);

	public E saveAndFlush(E entity);
}




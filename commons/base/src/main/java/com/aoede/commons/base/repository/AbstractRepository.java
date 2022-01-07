package com.aoede.commons.base.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;

import com.aoede.commons.base.entity.AbstractEntity;

/**
 * Definition of basic CRUD operations for entities
 */
@NoRepositoryBean
public interface AbstractRepository<EntityKey, Entity extends AbstractEntity<EntityKey>> {
	public List<Entity> findAll();

	public Optional<Entity> findById(EntityKey id);

	public void deleteById(EntityKey id);

	public boolean existsById(EntityKey id);

	public Entity save(Entity entity);

	public Entity saveAndFlush(Entity entity);
}




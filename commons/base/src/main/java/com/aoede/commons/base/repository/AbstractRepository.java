package com.aoede.commons.base.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;

import com.aoede.commons.base.entity.AbstractEntity;

/**
 * Definition of basic CRUD operations for entities
 */
@NoRepositoryBean
public interface AbstractRepository<EntityId, Entity extends AbstractEntity<EntityId>> {
	public List<Entity> findAll();

	public Optional<Entity> findById(EntityId id);

	public void deleteById(EntityId id);

	public boolean existsById(EntityId id);

	public Entity save(Entity entity);

	public Entity saveAndFlush(Entity entity);
}




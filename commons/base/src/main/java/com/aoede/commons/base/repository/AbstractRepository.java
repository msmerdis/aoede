package com.aoede.commons.base.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;

import com.aoede.commons.base.entity.AbstractEntity;

@NoRepositoryBean
public interface AbstractRepository<Key, Entity extends AbstractEntity<Key>> {
	public List<Entity> findAll();

	public Optional<Entity> findById(Key id);

	public void deleteById(Key id);

	public boolean existsById(Key id);

	public void save(Entity entity);

	public Entity saveAndFlush(Entity entity);
}




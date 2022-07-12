package com.aoede.commons.base.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.entity.AbstractEntity;

/**
 * Map repository is using a map to store data
 *
 * Implements all methods defined in AbstractRepository
 */
public class MapRepository<
	EntityKey, Entity extends AbstractEntity<EntityKey>
> extends BaseComponent implements AbstractRepository <EntityKey, Entity> {

	private Map<EntityKey, Entity> data;

	public MapRepository () {
		this(new HashMap <EntityKey, Entity>());
	}

	public MapRepository (Map<EntityKey, Entity> data) {
		this.data = data;
	}

	@Override
	public List<Entity> findAll() {
		return data.values().stream().collect(Collectors.toList());
	}

	@Override
	public Optional<Entity> findById(EntityKey id) {
		return Optional.ofNullable(data.get(id));
	}

	@Override
	public void deleteById(EntityKey id) {
		data.remove(id);
	}

	@Override
	public boolean existsById(EntityKey id) {
		return data.get(id) != null;
	}

	@Override
	public Entity save(Entity entity) {
		data.put(entity.getId(), entity);
		return entity;
	}

	@Override
	public Entity saveAndFlush(Entity entity) {
		return save(entity);
	}

}




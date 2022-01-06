package com.aoede.commons.base.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.entity.AbstractEntity;

/**
 * HashMap repository is using a hash map to store data
 *
 * Implements all methods defined in AbstractRepository
 */
public class HashMapRepository<
	EntityId, Entity extends AbstractEntity<EntityId>
> extends BaseComponent implements AbstractRepository <EntityId, Entity> {

	private HashMap<EntityId, Entity> data = new HashMap <EntityId, Entity>();

	@Override
	public List<Entity> findAll() {
		return data.values().stream().collect(Collectors.toList());
	}

	@Override
	public Optional<Entity> findById(EntityId id) {
		return Optional.ofNullable(data.get(id));
	}

	@Override
	public void deleteById(EntityId id) {
		data.remove(id);
	}

	@Override
	public boolean existsById(EntityId id) {
		return data.get(id) != null;
	}

	@Override
	public Entity save(Entity entity) {
		return data.put(entity.getId(), entity);
	}

	@Override
	public Entity saveAndFlush(Entity entity) {
		return save(entity);
	}

}




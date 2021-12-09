package com.aoede.commons.base.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.entity.AbstractEntity;

public class HashMapRepository<Key, Entity extends AbstractEntity<Key>> extends BaseComponent implements AbstractRepository <Key, Entity> {

	private HashMap<Key, Entity> data = new HashMap <Key, Entity>();

	@Override
	public List<Entity> findAll() {
		return data.values().stream().collect(Collectors.toList());
	}

	@Override
	public Optional<Entity> findById(Key id) {
		return Optional.ofNullable(data.get(id));
	}

	@Override
	public void deleteById(Key id) {
		data.remove(id);
	}

	@Override
	public boolean existsById(Key id) {
		return data.get(id) != null;
	}

	@Override
	public void save(Entity entity) {
		data.put(entity.getId(), entity);
	}

	@Override
	public Entity saveAndFlush(Entity entity) {
		save(entity);

		return entity;
	}

}




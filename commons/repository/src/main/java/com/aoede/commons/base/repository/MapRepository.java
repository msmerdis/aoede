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
	K, E extends AbstractEntity<K>
> extends BaseComponent implements AbstractRepository <K, E> {

	private Map<K, E> data;

	public MapRepository () {
		this(new HashMap<>());
	}

	public MapRepository (Map<K, E> data) {
		this.data = data;
	}

	@Override
	public List<E> findAll() {
		return data.values().stream().collect(Collectors.toList());
	}

	@Override
	public Optional<E> findById(K id) {
		return Optional.ofNullable(data.get(id));
	}

	@Override
	public void deleteById(K id) {
		data.remove(id);
	}

	@Override
	public boolean existsById(K id) {
		return data.get(id) != null;
	}

	@Override
	public E save(E entity) {
		data.put(entity.getId(), entity);
		return entity;
	}

	@Override
	public E saveAndFlush(E entity) {
		return save(entity);
	}

}




package com.aoede.commons.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.aoede.commons.base.BaseTestComponent;

abstract public class TestStorageServiceImpl<Data> extends BaseTestComponent implements TestStorageService<Data> {
	private HashMap<String, Data> storage = new HashMap<String, Data>();

	@Override
	public void clear() {
		storage.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return storage.containsKey(key);
	}

	@Override
	public boolean containsValue(Object data) {
		return storage.containsValue(data);
	}

	@Override
	public Set<Entry<String, Data>> entrySet() {
		return storage.entrySet();
	}

	@Override
	public Data get(Object key) {
		return storage.get(key);
	}

	@Override
	public boolean isEmpty() {
		return storage.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return storage.keySet();
	}

	@Override
	public Data put(String key, Data value) {
		return storage.put(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Data> data) {
		storage.putAll(data);
	}

	@Override
	public Data remove(Object data) {
		return storage.remove(data);
	}

	@Override
	public int size() {
		return storage.size();
	}

	@Override
	public Collection<Data> values() {
		return storage.values();
	}

}




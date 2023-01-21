package com.aoede.commons.base.entity;

/**
 * All entities must define a key
 */
public interface AbstractEntity<K> {
	K getId ();
}




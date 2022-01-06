package com.aoede.commons.base.entity;

/**
 * All entities must have a key
 */
public interface AbstractEntity<EntityId> {
	EntityId getId ();
}




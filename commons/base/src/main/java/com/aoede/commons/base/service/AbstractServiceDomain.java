package com.aoede.commons.base.service;

import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.commons.base.entity.AbstractEntity;
import com.aoede.commons.base.exceptions.GenericException;

public interface AbstractServiceDomain <
	Key,
	Domain extends AbstractDomain<Key>,
	Entity extends AbstractEntity<Key>
> extends AbstractService<Key, Domain> {
	public Entity createEntity (final Domain domain,                boolean includeParent, boolean cascade) throws GenericException;
	public void   updateEntity (final Domain domain, Entity entity, boolean includeParent, boolean cascade) throws GenericException;
	public Domain createDomain (final Entity entity,                boolean includeParent, boolean cascade);
	public void   updateDomain (final Entity entity, Domain domain, boolean includeParent, boolean cascade);
}




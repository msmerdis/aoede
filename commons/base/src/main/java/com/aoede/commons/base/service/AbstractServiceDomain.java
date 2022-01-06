package com.aoede.commons.base.service;

import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.commons.base.entity.AbstractEntity;
import com.aoede.commons.base.exceptions.GenericException;

public interface AbstractServiceDomain <
	DomainId, Domain extends AbstractDomain<DomainId>,
	EntityId, Entity extends AbstractEntity<EntityId>
> extends AbstractService<DomainId, Domain> {
	public Entity createEntity (final Domain domain,                      boolean includeParent, boolean cascade) throws GenericException;
	public void   updateEntity (final Domain domain, final Entity entity, boolean includeParent, boolean cascade) throws GenericException;
	public Domain createDomain (final Entity entity,                      boolean includeParent, boolean cascade);
	public void   updateDomain (final Entity entity, final Domain domain, boolean includeParent, boolean cascade);

	public EntityId createEntityId (final DomainId key);
}




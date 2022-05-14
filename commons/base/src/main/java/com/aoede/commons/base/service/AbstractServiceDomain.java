package com.aoede.commons.base.service;

import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.commons.base.entity.AbstractEntity;
import com.aoede.commons.base.exceptions.GenericException;

public interface AbstractServiceDomain <
	DomainKey, Domain extends AbstractDomain<DomainKey>,
	EntityKey, Entity extends AbstractEntity<EntityKey>
> extends AbstractService<DomainKey, Domain> {
	public Entity createEntity (final Domain domain                     ) throws GenericException;
	public void   updateEntity (final Domain domain, final Entity entity) throws GenericException;
	public Domain createDomain (final Entity entity                     );
	public void   updateDomain (final Entity entity, final Domain domain);

	public default boolean verifyDelete (final Entity entity) {
		return true;
	}

	public EntityKey createEntityKey (final DomainKey key);
}




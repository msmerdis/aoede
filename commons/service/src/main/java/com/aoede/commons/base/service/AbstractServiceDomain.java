package com.aoede.commons.base.service;

import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.commons.base.entity.AbstractEntity;
import com.aoede.commons.base.exceptions.GenericException;

public interface AbstractServiceDomain <
	K0, D extends AbstractDomain<K0>,
	K1, E extends AbstractEntity<K1>
> extends AbstractService<K0, D> {
	public E    createEntity (final D domain                ) throws GenericException;
	public void updateEntity (final D domain, final E entity) throws GenericException;
	public D    createDomain (final E entity                );
	public void updateDomain (final E entity, final D domain);

	public default boolean verifyDelete (final E entity) {
		return true;
	}

	public K1 createEntityKey (final K0 key);
}




package com.aoede.commons.base.service;

import java.util.List;

import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.commons.base.exceptions.GenericException;

/**
 * Definition of basic CRUD operations for domain objects
 */
public interface AbstractService <K, D extends AbstractDomain<K>> {
	D create(D domain) throws GenericException;

	void update(K id, D domain) throws GenericException;
	void delete(K id) throws GenericException;

	boolean exists(K id) throws GenericException;
	D find(K id) throws GenericException;

	List<D> findAll() throws GenericException;
	List<D> freeTextSearch (String keyword) throws GenericException;

	boolean supportsFreeTextSearch ();

	String domainName ();
}




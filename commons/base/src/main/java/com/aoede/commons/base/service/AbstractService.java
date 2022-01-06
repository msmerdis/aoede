package com.aoede.commons.base.service;

import java.util.List;

import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.commons.base.exceptions.GenericException;

/**
 * Definition of basic CRUD operations for domain objects
 */
public interface AbstractService <DomainId, Domain extends AbstractDomain<DomainId>> {
	Domain create(Domain domain) throws GenericException, Exception;

	void update(DomainId id, Domain domain) throws GenericException;
	void delete(DomainId id) throws GenericException;

	boolean exists(DomainId id) throws GenericException;
	Domain find(DomainId id) throws GenericException;

	List<Domain> findAll() throws GenericException;
	List<Domain> freeTextSearch (String keyword) throws GenericException;

	boolean supportsFreeTextSearch ();

	String domainName ();
}




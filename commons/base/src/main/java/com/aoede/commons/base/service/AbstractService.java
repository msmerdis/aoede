package com.aoede.commons.base.service;

import java.util.List;

import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.commons.base.exceptions.GenericException;

/**
 * Definition of basic CRUD operations for domain objects
 */
public interface AbstractService <DomainKey, Domain extends AbstractDomain<DomainKey>> {
	Domain create(Domain domain) throws GenericException, Exception;

	void update(DomainKey id, Domain domain) throws GenericException;
	void delete(DomainKey id) throws GenericException;

	boolean exists(DomainKey id) throws GenericException;
	Domain find(DomainKey id) throws GenericException;

	List<Domain> findAll() throws GenericException;
	List<Domain> freeTextSearch (String keyword) throws GenericException;

	boolean supportsFreeTextSearch ();

	String domainName ();
}




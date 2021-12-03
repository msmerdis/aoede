package com.aoede.commons.base.service;

import java.util.List;

import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.commons.base.exceptions.GenericException;

public interface AbstractService <Key, Domain extends AbstractDomain<Key>> {
	Domain create(Domain domain) throws GenericException, Exception;

	void update(Domain domain) throws GenericException;
	void delete(Key id) throws GenericException;

	boolean exists(Key id) throws GenericException;
	Domain find(Key id) throws GenericException;

	List<Domain> findAll() throws GenericException;
	List<Domain> freeTextSearch (String keyword) throws GenericException;

	boolean supportsFreeTextSearch ();

	String domainName ();
}




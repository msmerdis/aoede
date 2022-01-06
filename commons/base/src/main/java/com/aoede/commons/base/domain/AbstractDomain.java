package com.aoede.commons.base.domain;

/**
 * Definition of the base domain class
 *
 * Defines a key for each domain class, keys are mandatory for all domain objects
 */
public interface AbstractDomain<DomainId> {
	DomainId getId ();
	void setId (DomainId id);
}




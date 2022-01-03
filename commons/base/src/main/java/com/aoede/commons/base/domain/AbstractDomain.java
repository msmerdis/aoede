package com.aoede.commons.base.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Definition of the base domain class
 *
 * Defines a key for each domain class, keys are mandatory for all domain objects
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractDomain<Key> {
	private Key id;
}




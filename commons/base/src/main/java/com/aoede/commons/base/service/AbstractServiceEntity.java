package com.aoede.commons.base.service;

import com.aoede.commons.base.domain.AbstractDomain;

public interface AbstractServiceEntity <Key, Domain extends AbstractDomain<Key>> extends AbstractService<Key, Domain> {
}




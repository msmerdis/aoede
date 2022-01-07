package com.aoede.commons.test.simplekey;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.AbstractJpaRepository;

@Repository
public interface SimpleKeyRepository extends AbstractJpaRepository <Integer, SimpleKeyEntity> {
}




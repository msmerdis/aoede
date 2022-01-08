package com.aoede.commons.test.compositekey;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.AbstractJpaRepository;

@Repository
public interface CompositeKeyRepository extends AbstractJpaRepository <CompositeKeyEntityKey, CompositeKeyEntity> {
}




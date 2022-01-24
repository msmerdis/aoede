package com.aoede.modules.user.repository;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.AbstractJpaRepository;
import com.aoede.modules.user.entity.RoleEntity;

@Repository
public interface RoleRepository extends AbstractJpaRepository <Integer, RoleEntity> {
}




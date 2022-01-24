package com.aoede.modules.user.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.AbstractJpaRepository;
import com.aoede.modules.user.entity.UserEntity;

@Repository
public interface UserRepository extends AbstractJpaRepository <Long, UserEntity> {

	Optional<UserEntity> findByUsername(String username);

}




package com.aoede.modules.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.aoede.commons.base.service.AbstractServiceDomain;
import com.aoede.modules.user.domain.User;
import com.aoede.modules.user.entity.UserEntity;

public interface UserService extends AbstractServiceDomain <Long, User, Long, UserEntity>, UserDetailsService {
}




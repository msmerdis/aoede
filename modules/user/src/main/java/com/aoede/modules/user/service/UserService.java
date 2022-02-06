package com.aoede.modules.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.service.AbstractServiceDomain;
import com.aoede.modules.user.domain.User;
import com.aoede.modules.user.entity.UserEntity;

public interface UserService extends AbstractServiceDomain <Long, User, Long, UserEntity>, UserDetailsService {
	User loadUserByUsername(String username) throws UsernameNotFoundException;
	User login(String username, String password) throws UsernameNotFoundException, GenericException;
	Long currentUserId ();
}




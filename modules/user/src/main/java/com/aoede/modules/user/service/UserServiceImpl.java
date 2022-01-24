package com.aoede.modules.user.service;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.service.AbstractServiceDomainImpl;
import com.aoede.modules.user.domain.User;
import com.aoede.modules.user.entity.UserEntity;
import com.aoede.modules.user.repository.UserRepository;

@Service
public class UserServiceImpl extends AbstractServiceDomainImpl <Long, User, Long, UserEntity, UserRepository> implements UserService {
	private RoleService roleService;

	public UserServiceImpl(UserRepository repository, EntityManagerFactory entityManagerFactory, RoleService roleService) {
		super(repository, entityManagerFactory);
		this.roleService = roleService;
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return "user";
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> entity = repository.findByUsername(username);

		if (entity.isEmpty()) {
			throw new AuthenticationServiceException("user not found");
		}

		return createDomain(entity.get(), true, true);
	}

	@Override
	public UserEntity createEntity(User domain, boolean includeParent, boolean cascade) throws GenericException {
		UserEntity entity = new UserEntity();

		updateEntity(domain, entity, includeParent, cascade);

		entity.setId(domain.getId());

		return entity;
	}

	@Override
	public void updateEntity(User domain, UserEntity entity, boolean includeParent, boolean cascade) throws GenericException {
		entity.setStatus(domain.getStatus());
		entity.setUsername(domain.getUsername());
		entity.setPassword(domain.getPassword());
		entity.setPasssalt(domain.getPasssalt());
	}

	@Override
	public User createDomain(UserEntity entity, boolean includeParent, boolean cascade) {
		User domain = new User ();

		updateDomain(entity, domain, includeParent, cascade);

		return domain;
	}

	@Override
	public void updateDomain(UserEntity entity, User domain, boolean includeParent, boolean cascade) {
		domain.setId(entity.getId());
		domain.setStatus(entity.getStatus());
		domain.setUsername(entity.getUsername());
		domain.setPassword(entity.getPassword());
		domain.setPasssalt(entity.getPasssalt());

		if (cascade) {
			domain.setAuthorities(
				entity.getRoles().stream()
					.map(r -> roleService.createDomain(r, false, false))
					.collect(Collectors.toSet())
			);
		}
	}

	@Override
	public Long createEntityKey(Long key) {
		return key;
	}

}




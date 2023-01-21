package com.aoede.modules.user.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.exceptions.GenericExceptionContainer;
import com.aoede.commons.base.exceptions.UnauthorizedException;
import com.aoede.commons.base.service.AbstractServiceDomainImpl;
import com.aoede.modules.user.domain.User;
import com.aoede.modules.user.entity.UserEntity;
import com.aoede.modules.user.repository.UserRepository;
import com.aoede.modules.user.transfer.user.UserStatus;

@Service
public class UserServiceImpl extends AbstractServiceDomainImpl <Long, User, Long, UserEntity, UserRepository> implements UserService {

	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
		super(repository);

		this.passwordEncoder = passwordEncoder;
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
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> entity = repository.findByUsername(username);

		if (entity.isEmpty()) {
			throw new UsernameNotFoundException("user not found");
		}

		return createDomain(entity.get());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public User login(String username, String password) throws UsernameNotFoundException, GenericException {
		User user = loadUserByUsername(username);

		if (
			passwordEncoder.matches(password, user.getPassword()) &&
			user.getStatus().equals(UserStatus.ACTIVE)
		) {
			return user;
		}

		throw new UnauthorizedException("Unable to authenticate user");
	}

	@Override
	public UserEntity createEntity(User domain) throws GenericException {
		UserEntity entity = new UserEntity();

		entity.setStatus(domain.getStatus());
		entity.setUsername(domain.getUsername());
		entity.setPassword(
			passwordEncoder.encode(domain.getPassword())
		);

		return entity;
	}

	@Override
	public void updateEntity(User domain, UserEntity entity) throws GenericException {
		if (domain.getStatus() != null)
			entity.setStatus(domain.getStatus());

		if (domain.getUsername() != null)
			entity.setUsername(domain.getUsername());

		if (domain.getPassword() != null)
			entity.setPassword(
				passwordEncoder.encode(domain.getPassword())
			);
	}

	@Override
	public User createDomain(UserEntity entity) {
		User domain = new User ();

		updateDomain(entity, domain);

		return domain;
	}

	@Override
	public void updateDomain(UserEntity entity, User domain) {
		domain.setId(entity.getId());
		domain.setStatus(entity.getStatus());
		domain.setUsername(entity.getUsername());
		domain.setPassword(entity.getPassword());
		domain.setRoles(
			entity.getRoles().stream()
				.map(r -> new SimpleGrantedAuthority(r.getRole()))
				.collect(Collectors.toSet())
		);
	}

	@Override
	public Long createEntityKey(Long key) {
		return key;
	}

	@Override
	public Long currentUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			throw new GenericExceptionContainer (
				new UnauthorizedException("No user information available")
			);
		}

		return (Long) authentication.getPrincipal();
	}

}




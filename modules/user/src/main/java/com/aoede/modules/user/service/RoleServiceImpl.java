package com.aoede.modules.user.service;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Service;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.service.AbstractServiceDomainImpl;
import com.aoede.modules.user.domain.Role;
import com.aoede.modules.user.entity.RoleEntity;
import com.aoede.modules.user.repository.RoleRepository;

@Service
public class RoleServiceImpl extends AbstractServiceDomainImpl <Integer, Role, Integer, RoleEntity, RoleRepository> implements RoleService {

	public RoleServiceImpl(RoleRepository repository, EntityManagerFactory entityManagerFactory) {
		super(repository, entityManagerFactory);
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return "role";
	}

	@Override
	public RoleEntity createEntity(Role domain, boolean includeParent, boolean cascade) throws GenericException {
		RoleEntity entity = new RoleEntity();

		updateEntity(domain, entity, includeParent, cascade);

		entity.setId(domain.getId());

		return entity;
	}

	@Override
	public void updateEntity(Role domain, RoleEntity entity, boolean includeParent, boolean cascade) throws GenericException {
		entity.setDesc(domain.getAuthority());
		entity.setRole(domain.getAuthority());
	}

	@Override
	public Role createDomain(RoleEntity entity, boolean includeParent, boolean cascade) {
		Role role = new Role();

		updateDomain(entity, role, includeParent, cascade);

		return role;
	}

	@Override
	public void updateDomain(RoleEntity entity, Role domain, boolean includeParent, boolean cascade) {
		domain.setAuthority(entity.getRole());
		domain.setId(entity.getId());
	}

	@Override
	public Integer createEntityKey(Integer key) {
		return key;
	}

}




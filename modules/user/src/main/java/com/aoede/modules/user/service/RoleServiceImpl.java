package com.aoede.modules.user.service;

import org.springframework.stereotype.Service;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.service.AbstractServiceDomainImpl;
import com.aoede.modules.user.domain.Role;
import com.aoede.modules.user.entity.RoleEntity;
import com.aoede.modules.user.repository.RoleRepository;

@Service
public class RoleServiceImpl extends AbstractServiceDomainImpl <Integer, Role, Integer, RoleEntity, RoleRepository> implements RoleService {

	public RoleServiceImpl(RoleRepository repository) {
		super(repository);
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
	public RoleEntity createEntity(Role domain) throws GenericException {
		RoleEntity entity = new RoleEntity();

		updateEntity(domain, entity);

		entity.setId(domain.getId());

		return entity;
	}

	@Override
	public void updateEntity(Role domain, RoleEntity entity) throws GenericException {
		entity.setDesc(domain.getDesc());
		entity.setRole(domain.getRole());
	}

	@Override
	public Role createDomain(RoleEntity entity) {
		Role role = new Role();

		updateDomain(entity, role);

		return role;
	}

	@Override
	public void updateDomain(RoleEntity entity, Role domain) {
		domain.setId(entity.getId());
		domain.setRole(entity.getRole());
		domain.setDesc(entity.getDesc());
	}

	@Override
	public Integer createEntityKey(Integer key) {
		return key;
	}

}




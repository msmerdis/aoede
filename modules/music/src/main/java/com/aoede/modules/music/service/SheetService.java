package com.aoede.modules.music.service;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Service;

import com.aoede.commons.base.service.AbstractServiceDomainImpl;
import com.aoede.modules.music.domain.Sheet;
import com.aoede.modules.music.entity.SheetEntity;
import com.aoede.modules.music.repository.SheetRepository;

@Service
public class SheetService extends AbstractServiceDomainImpl <Long, Sheet, SheetEntity, SheetRepository> {

	public SheetService(SheetRepository repository, EntityManagerFactory entityManagerFactory) {
		super(repository, entityManagerFactory);
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return null;
	}

	@Override
	protected SheetEntity createEntity(Sheet domain) {
		SheetEntity entity = new SheetEntity ();

		updateEntity (domain, entity);

		return entity;
	}

	@Override
	protected void updateEntity(Sheet domain, SheetEntity entity) {
		entity.setName(domain.getName());
	}

	@Override
	protected Sheet createDomain(SheetEntity entity) {
		Sheet sheet = new Sheet ();

		updateDomain (entity, sheet);

		return sheet;
	}

	@Override
	protected void updateDomain(SheetEntity entity, Sheet domain) {
		domain.setId(entity.getId());
		domain.setName(entity.getName());
	}

}




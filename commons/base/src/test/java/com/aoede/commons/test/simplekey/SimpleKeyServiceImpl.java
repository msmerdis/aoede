package com.aoede.commons.test.simplekey;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aoede.commons.base.exceptions.BadRequestException;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.service.AbstractServiceDomainImpl;

@Service
public class SimpleKeyServiceImpl extends AbstractServiceDomainImpl <Integer, SimpleKeyDomain, Integer, SimpleKeyEntity, SimpleKeyRepository> implements SimpleKeyService {

	@Autowired
	public SimpleKeyServiceImpl(SimpleKeyRepository repository, EntityManagerFactory entityManagerFactory) {
		super(repository, entityManagerFactory);
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return "SimpleKeyDomain";
	}

	@Override
	public SimpleKeyEntity createEntity(SimpleKeyDomain domain, boolean includeParent, boolean cascade) throws GenericException {
		SimpleKeyEntity simpleKeyEntity = new SimpleKeyEntity ();

		if (domain.getId() != null)
			throw new BadRequestException("SimpleKeyEntity must be auto generated");

		simpleKeyEntity.setValue(domain.getValue());

		return simpleKeyEntity;
	}

	@Override
	public void updateEntity(SimpleKeyDomain domain, SimpleKeyEntity entity, boolean includeParent, boolean cascade) throws GenericException {
		if (domain.getId() != null) {
			if (domain.getId() != entity.getId())
				throw new BadRequestException("SimpleKeyEntity's id cannot be updated");

			entity.setId(domain.getId());
		}
		entity.setValue(domain.getValue());
	}

	@Override
	public SimpleKeyDomain createDomain(SimpleKeyEntity entity, boolean includeParent, boolean cascade) {
		return new SimpleKeyDomain (entity.getId(), entity.getValue());
	}

	@Override
	public void updateDomain(SimpleKeyEntity entity, SimpleKeyDomain domain, boolean includeParent, boolean cascade) {
		domain.setId(entity.getId());
		domain.setValue(entity.getValue());
	}

	@Override
	public Integer createEntityKey(Integer key) {
		return key;
	}

}




package com.aoede.commons.base.compositekey;

import org.springframework.stereotype.Service;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.service.AbstractServiceDomainImpl;

@Service
public class CompositeKeyServiceImpl extends AbstractServiceDomainImpl <CompositeKeyDomainKey, CompositeKeyDomain, CompositeKeyEntityKey, CompositeKeyEntity, CompositeKeyRepository> implements CompositeKeyService {

	public CompositeKeyServiceImpl(CompositeKeyRepository repository) {
		super(repository);
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return "CompositeKeyDomain";
	}

	@Override
	public CompositeKeyEntity createEntity(CompositeKeyDomain domain) throws GenericException {
		CompositeKeyEntity compositeKeyEntity = new CompositeKeyEntity ();

		compositeKeyEntity.setId(createEntityKey(domain.getId()));
		compositeKeyEntity.setValue(domain.getValue());

		return compositeKeyEntity;
	}

	@Override
	public void updateEntity(CompositeKeyDomain domain, CompositeKeyEntity entity) throws GenericException {
		entity.setValue(domain.getValue());
	}

	@Override
	public CompositeKeyDomain createDomain(CompositeKeyEntity entity) {
		return new CompositeKeyDomain (createDomainKey(entity.getId()), entity.getValue());
	}

	@Override
	public void updateDomain(CompositeKeyEntity entity, CompositeKeyDomain domain) {
		domain.setId(createDomainKey(entity.getId()));
		domain.setValue(entity.getValue());
	}

	@Override
	public CompositeKeyEntityKey createEntityKey(CompositeKeyDomainKey key) {
		CompositeKeyEntityKey compositeKeyEntityKey = new CompositeKeyEntityKey ();

		compositeKeyEntityKey.setChildId(key.getChildId());
		compositeKeyEntityKey.setParentId(key.getParentId());

		return compositeKeyEntityKey;
	}

	public CompositeKeyDomainKey createDomainKey(CompositeKeyEntityKey key) {
		CompositeKeyDomainKey compositeKeyDomainKey = new CompositeKeyDomainKey ();

		compositeKeyDomainKey.setChildId(key.getChildId());
		compositeKeyDomainKey.setParentId(key.getParentId());

		return compositeKeyDomainKey;
	}

}




package com.aoede.commons.base.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.commons.base.entity.AbstractEntity;
import com.aoede.commons.base.exceptions.ConflictException;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.exceptions.NotFoundException;
import com.aoede.commons.base.exceptions.NotImplementedException;
import com.aoede.commons.base.exceptions.UnauthorizedException;
import com.aoede.commons.base.repository.AbstractRepository;

/**
 * D service can be used in case the domain object is used
 * as an entity as well
 *
 * Service will convert domain objects to entities and vice versa
 * when communicating with the repository
 */
public abstract class AbstractServiceDomainImpl <
	K0, D extends AbstractDomain<K0>,
	K1, E extends AbstractEntity<K1>,
	R extends AbstractRepository<K1, E>
> extends BaseComponent implements AbstractServiceDomain<K0, D, K1, E> {

	protected final R repository;

	protected AbstractServiceDomainImpl (R repository) {
		this.repository  = repository;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public D create(D domain) throws GenericException {
		E entity = createEntity(domain);

		try {
			entity = repository.saveAndFlush(entity);
		} catch (Exception e) {
			throw new ConflictException(domainName() + " could not be created");
		}

		updateDomain(entity, domain);

		return domain;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void update(K0 id, D domain) throws GenericException {
		Optional<E> optionalEntity = repository.findById(createEntityKey(id));

		if (optionalEntity.isEmpty()) {
			throw new NotFoundException(domainName() + " not found.");
		}

		E entity = optionalEntity.get();

		updateEntity(domain, entity);

		try {
			repository.saveAndFlush(entity);
		} catch (Exception e) {
			throw new ConflictException(domainName() + " could not be updated");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void delete(K0 id) throws GenericException {
		K1 key = createEntityKey(id);
		Optional<E> entity = repository.findById(key);

		if (entity.isEmpty()) {
			throw new NotFoundException(domainName() + " not found.");
		}

		if (!verifyDelete(entity.get())) {
			throw new UnauthorizedException("Cannot delete " + domainName());
		}

		repository.deleteById(key);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public boolean exists(K0 id) throws GenericException {
		return repository.existsById(createEntityKey(id));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public D find(K0 id) throws GenericException {
		Optional<E> entity = repository.findById(createEntityKey(id));

		if (entity.isEmpty()) {
			throw new NotFoundException("E not found.");
		}

		return createDomain(entity.get());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<D> findAll() throws GenericException {
		return repository.findAll().stream().map(this::createDomain).collect(Collectors.toList());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<D> freeTextSearch(String keyword) throws GenericException {
		throw new NotImplementedException("Free text search not implemented for : " + domainName());
	}
}




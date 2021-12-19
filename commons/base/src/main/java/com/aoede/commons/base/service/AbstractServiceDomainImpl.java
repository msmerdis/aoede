package com.aoede.commons.base.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

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
import com.aoede.commons.base.repository.AbstractRepository;

public abstract class AbstractServiceDomainImpl <
	Key,
	Domain extends AbstractDomain<Key>,
	Entity extends AbstractEntity<Key>,
	Repository extends AbstractRepository<Key, Entity>
> extends BaseComponent implements AbstractService<Key, Domain> {

	final protected Repository repository;

	public AbstractServiceDomainImpl (Repository repository, EntityManagerFactory entityManagerFactory) {
		this.repository  = repository;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public Domain create(Domain domain) throws GenericException {
		Entity entity = createEntity(domain);

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
	public void update(Key id, Domain domain) throws GenericException {
		Optional<Entity> optionalEntity = repository.findById(id);

		if (optionalEntity.isEmpty()) {
			throw new NotFoundException(domainName() + " not found.");
		}

		Entity entity = optionalEntity.get();

		updateEntity(domain, entity);

		try {
			repository.saveAndFlush(entity);
		} catch (Exception e) {
			throw new ConflictException(domainName() + " could not be updated");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void delete(Key id) throws GenericException {
		Optional<Entity> entity = repository.findById(id);

		if (entity.isEmpty()) {
			throw new NotFoundException(domainName() + " not found.");
		}

		repository.deleteById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public boolean exists(Key id) throws GenericException {
		return repository.existsById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public Domain find(Key id) throws GenericException {
		Optional<Entity> entity = repository.findById(id);

		if (entity.isEmpty()) {
			throw new NotFoundException("Entity not found.");
		}

		return createDomain(entity.get());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Domain> findAll() throws GenericException {
		return repository.findAll().stream().map(e -> createDomain(e)).collect(Collectors.toList());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Domain> freeTextSearch(String keyword) throws GenericException {
		throw new NotImplementedException("Free text search not implemented for : " + domainName());
	}

	abstract protected Entity createEntity (final Domain domain) throws GenericException;
	abstract protected void   updateEntity (final Domain domain, Entity entity) throws GenericException;
	abstract protected Domain createDomain (final Entity entity);
	abstract protected void   updateDomain (final Entity entity, Domain domain);
}




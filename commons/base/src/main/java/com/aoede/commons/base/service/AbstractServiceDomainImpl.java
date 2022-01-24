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

/**
 * Domain service can be used in case the domain object is used
 * as an entity as well
 *
 * Service will convert domain objects to entities and vice versa
 * when communicating with the repository
 */
public abstract class AbstractServiceDomainImpl <
	DomainKey, Domain extends AbstractDomain<DomainKey>,
	EntityKey, Entity extends AbstractEntity<EntityKey>,
	Repository extends AbstractRepository<EntityKey, Entity>
> extends BaseComponent implements AbstractServiceDomain<DomainKey, Domain, EntityKey, Entity> {

	final protected Repository repository;

	public AbstractServiceDomainImpl (Repository repository, EntityManagerFactory entityManagerFactory) {
		this.repository  = repository;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public Domain create(Domain domain) throws GenericException {
		Entity entity = createEntity(domain, true, true);

		try {
			entity = repository.saveAndFlush(entity);
		} catch (Exception e) {
			throw new ConflictException(domainName() + " could not be created");
		}

		updateDomain(entity, domain, true, true);

		return domain;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void update(DomainKey id, Domain domain) throws GenericException {
		Optional<Entity> optionalEntity = repository.findById(createEntityKey(id));

		if (optionalEntity.isEmpty()) {
			throw new NotFoundException(domainName() + " not found.");
		}

		Entity entity = optionalEntity.get();

		updateEntity(domain, entity, true, true);

		try {
			repository.saveAndFlush(entity);
		} catch (Exception e) {
			throw new ConflictException(domainName() + " could not be updated");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void delete(DomainKey id) throws GenericException {
		EntityKey key = createEntityKey(id);
		Optional<Entity> entity = repository.findById(key);

		if (entity.isEmpty()) {
			throw new NotFoundException(domainName() + " not found.");
		}

		repository.deleteById(key);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public boolean exists(DomainKey id) throws GenericException {
		return repository.existsById(createEntityKey(id));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public Domain find(DomainKey id) throws GenericException {
		Optional<Entity> entity = repository.findById(createEntityKey(id));

		if (entity.isEmpty()) {
			throw new NotFoundException("Entity not found.");
		}

		return createDomain(entity.get(), true, true);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Domain> findAll() throws GenericException {
		return repository.findAll().stream().map(e -> createDomain(e, true, true)).collect(Collectors.toList());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Domain> freeTextSearch(String keyword) throws GenericException {
		throw new NotImplementedException("Free text search not implemented for : " + domainName());
	}
}




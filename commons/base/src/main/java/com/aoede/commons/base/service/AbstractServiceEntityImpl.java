package com.aoede.commons.base.service;

import java.util.List;
import java.util.Optional;

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
 * Entity service can be used in case the domain object is used
 * as an entity as well
 *
 * Service will pass and return the object as is to the repository layer
 */
public abstract class AbstractServiceEntityImpl <
	Key,
	Domain extends AbstractDomain<Key> & AbstractEntity<Key>,
	Repository extends AbstractRepository<Key, Domain>
> extends BaseComponent implements AbstractService<Key, Domain> {

	final protected Repository repository;

	public AbstractServiceEntityImpl (Repository repository, EntityManagerFactory entityManagerFactory) {
		this.repository  = repository;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public Domain create(Domain domain) throws GenericException {
		Optional<Domain> optionalDomain = repository.findById(domain.getId());

		if (optionalDomain.isPresent()) {
			throw new ConflictException(domainName() + " already exists");
		}

		try {
			return repository.saveAndFlush(domain);
		} catch (Exception e) {
			throw new ConflictException(domainName() + " could not be created");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void update(Key id, Domain domain) throws GenericException {
		Optional<Domain> optionalDomain = repository.findById(id);

		if (optionalDomain.isEmpty()) {
			throw new NotFoundException(domainName() + " not found.");
		}

		if (id != domain.getId()) {
			repository.deleteById(id);
		}

		try {
			repository.save(domain);
		} catch (Exception e) {
			throw new ConflictException(domainName() + " could not be updated");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void delete(Key id) throws GenericException {
		Optional<Domain> entity = repository.findById(id);

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
		Optional<Domain> domain = repository.findById(id);

		if (domain.isEmpty()) {
			throw new NotFoundException("Entity not found.");
		}

		return domain.get();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Domain> findAll() throws GenericException {
		return repository.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Domain> freeTextSearch(String keyword) throws GenericException {
		throw new NotImplementedException("Free text search not implemented for : " + domainName());
	}
}




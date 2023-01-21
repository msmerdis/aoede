package com.aoede.commons.base.service;

import java.util.List;
import java.util.Optional;

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
	K,
	D extends AbstractDomain<K> & AbstractEntity<K>,
	R extends AbstractRepository<K, D>
> extends BaseComponent implements AbstractService<K, D> {

	protected final R repository;

	protected AbstractServiceEntityImpl (R repository) {
		this.repository  = repository;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public D create(D domain) throws GenericException {
		if (repository.existsById(domain.getId())) {
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
	public void update(K id, D domain) throws GenericException {
		Optional<D> optionalDomain = repository.findById(id);

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
	public void delete(K id) throws GenericException {
		Optional<D> entity = repository.findById(id);

		if (entity.isEmpty()) {
			throw new NotFoundException(domainName() + " not found.");
		}

		repository.deleteById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public boolean exists(K id) throws GenericException {
		return repository.existsById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public D find(K id) throws GenericException {
		Optional<D> domain = repository.findById(id);

		if (domain.isEmpty()) {
			throw new NotFoundException(domainName() + " not found.");
		}

		return domain.get();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<D> findAll() throws GenericException {
		return repository.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<D> freeTextSearch(String keyword) throws GenericException {
		throw new NotImplementedException("Free text search not implemented for : " + domainName());
	}
}




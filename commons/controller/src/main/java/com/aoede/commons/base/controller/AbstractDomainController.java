package com.aoede.commons.base.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.commons.base.service.AbstractService;

/**
 * D controller can be used in case the domain object is used
 * as a request and response object as well
 *
 * Controller will pass and return the object as is to the service layer
 */
public abstract class AbstractDomainController<
	K,
	D extends AbstractDomain<K>,
	S extends AbstractService<K, D>
> extends AbstractController <K, D, K, D, D, D, D> {

	protected final S service;

	protected AbstractDomainController (S service) {
		this.service = service;
	}

	@Override
	public List<D> search(@RequestHeader("X-Search-Terms") String keyword) throws Exception {
		return service.freeTextSearch(keyword);
	}

	@Override
	public D get(@PathVariable("id") final K id) throws Exception {
		return service.find(id);
	}

	@Override
	public List<D> findAll() throws Exception {
		return service.findAll();
	}

	@Override
	public D create(@Valid @RequestBody final D domain) throws Exception {
		return service.create(domain);
	}

	@Override
	public void update(@PathVariable("id") final K id, @Valid @RequestBody final D domain) throws Exception {
		service.update(id, domain);
	}

	@Override
	public void delete(@PathVariable("id") final K id) throws Exception {
		service.delete(id);
	}
}




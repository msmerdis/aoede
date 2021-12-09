package com.aoede.commons.base.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.commons.base.service.AbstractService;

public abstract class AbstractDomainController<
	Key,
	Domain extends AbstractDomain<Key>,
	Service extends AbstractService<Key, Domain>
> extends AbstractController <Key, Domain, Domain, Domain, Domain, Domain, Service> {

	final protected Service service;

	@Autowired
	public AbstractDomainController (Service service) {
		this.service = service;
	}

	@Override
	public List<Domain> search(@RequestHeader("X-Search-Terms") String keyword) throws Exception {
		return service.freeTextSearch(keyword);
	}

	@Override
	public Domain get(@PathVariable("id") final Key id) throws Exception {
		return service.find(id);
	}

	@Override
	public List<Domain> findAll() throws Exception {
		return service.findAll();
	}

	@Override
	public Domain create(@Valid @RequestBody final Domain domain) throws Exception {
		return service.create(domain);
	}

	@Override
	public void update(@Valid @RequestBody final Domain domain) throws Exception {
		service.update(domain);
	}

	@Override
	public void delete(@PathVariable("id") final Key id) throws Exception {
		service.delete(id);
	}
}




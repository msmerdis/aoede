package com.aoede.commons.base.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.commons.base.service.AbstractService;

/**
 * Resource controller can be used in case the domain object differs
 * from the request and response objects
 *
 * Controller will convert the request objects to a domain class before
 * passing it to the service layer and additional convert the resulting domain classes
 * into the response objects before returning them as a result
 */
public abstract class AbstractResourceController<
	Key,
	Domain extends AbstractDomain<Key>,
	CreateRequest,
	UpdateRequest,
	SimpleResponse,
	DetailResponse,
	Service extends AbstractService<Key, Domain>
> extends AbstractController <Key, Domain, CreateRequest, UpdateRequest, SimpleResponse, DetailResponse, Service> {

	final protected Service service;

	@Autowired
	public AbstractResourceController (Service service) {
		this.service = service;
	}

	@Override
	public List<SimpleResponse> search(@RequestHeader("X-Search-Terms") String keyword) throws Exception {
		return service.freeTextSearch(keyword).stream().map(e -> simpleResponse(e, true, true)).collect(Collectors.toList());
	}

	@Override
	public DetailResponse get(@PathVariable("id") final Key id) throws Exception {
		return detailResponse(service.find(id), true, true);
	}

	@Override
	public List<SimpleResponse> findAll() throws Exception {
		return service.findAll().stream().map(e -> simpleResponse(e, true, true)).collect(Collectors.toList());
	}

	@Override
	public DetailResponse create(@Valid @RequestBody final CreateRequest request) throws Exception {
		return detailResponse(service.create(createRequest(request)), true, true);
	}

	@Override
	public void update(@PathVariable("id") final Key id, @Valid @RequestBody final UpdateRequest request) throws Exception {
		Domain domain = updateRequest(request);

		domain.setId(id);

		service.update(id, domain);
	}

	@Override
	public void delete(@PathVariable("id") final Key id) throws Exception {
		service.delete(id);
	}

	/**
	 * operations to display a domain object are abstact and should be defined by each
	 * implementing class.
	 *
	 * To resolve circular dependencies options to include parent and children are included
	 */
	abstract public SimpleResponse simpleResponse (Domain entity, boolean includeParent, boolean cascade);
	abstract public DetailResponse detailResponse (Domain entity, boolean includeParent, boolean cascade);

	/**
	 * operations to generate the domain class from the create and update objects are abstract
	 * and should be defined by each implementing class.
	 */
	abstract public Domain createRequest (CreateRequest request);
	abstract public Domain updateRequest (UpdateRequest request);
}




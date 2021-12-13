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
		return service.freeTextSearch(keyword).stream().map(e -> simpleResponse(e)).collect(Collectors.toList());
	}

	@Override
	public DetailResponse get(@PathVariable("id") final Key id) throws Exception {
		return detailResponse(service.find(id));
	}

	@Override
	public List<SimpleResponse> findAll() throws Exception {
		return service.findAll().stream().map(e -> simpleResponse(e)).collect(Collectors.toList());
	}

	@Override
	public DetailResponse create(@Valid @RequestBody final CreateRequest request) throws Exception {
		return detailResponse(service.create(createRequest(request)));
	}

	@Override
	public void update(@PathVariable("id") final Key id, @Valid @RequestBody final UpdateRequest request) throws Exception {
		service.update(id, updateRequest(request));
	}

	@Override
	public void delete(@PathVariable("id") final Key id) throws Exception {
		service.delete(id);
	}

	abstract protected SimpleResponse simpleResponse (Domain entity);
	abstract protected DetailResponse detailResponse (Domain entity);

	abstract protected Domain createRequest (CreateRequest request);
	abstract protected Domain updateRequest (UpdateRequest request);
}




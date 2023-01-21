package com.aoede.commons.base.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.commons.base.service.AbstractService;
import com.aoede.commons.base.transfer.AbstractResponse;

/**
 * Resource controller can be used in case the domain object differs
 * from the request and response objects
 *
 * Controller will convert the request objects to a domain class before
 * passing it to the service layer and additional convert the resulting domain classes
 * into the response objects before returning them as a result
 */
public abstract class AbstractResourceController<
	DomainKey,
	Domain extends AbstractDomain<DomainKey>,
	AccessKey,
	CreateData,
	UpdateData,
	SimpleResponse extends AbstractResponse <AccessKey>,
	DetailResponse extends AbstractResponse <AccessKey>,
	Service extends AbstractService<DomainKey, Domain>
> extends AbstractController <DomainKey, Domain, AccessKey, CreateData, UpdateData, SimpleResponse, DetailResponse> {

	protected final Service service;

	protected AbstractResourceController (Service service) {
		this.service = service;
	}

	@Override
	public List<SimpleResponse> search(@RequestHeader("X-Search-Terms") String keyword) throws Exception {
		return service.freeTextSearch(keyword).stream().map(this::simpleResponse).collect(Collectors.toList());
	}

	@Override
	public DetailResponse get(@PathVariable("id") final AccessKey id) throws Exception {
		return detailResponse(service.find(createDomainKey(id)));
	}

	@Override
	public List<SimpleResponse> findAll() throws Exception {
		return service.findAll().stream().map(this::simpleResponse).collect(Collectors.toList());
	}

	@Override
	public DetailResponse create(@Valid @RequestBody final CreateData data) throws Exception {
		return detailResponse(service.create(createDomain(data)));
	}

	@Override
	public void update(@PathVariable("id") final AccessKey id, @Valid @RequestBody final UpdateData data) throws Exception {
		service.update(createDomainKey(id), updateDomain(data));
	}

	@Override
	public void delete(@PathVariable("id") final AccessKey id) throws Exception {
		service.delete(createDomainKey(id));
	}

	/**
	 * operations to display a domain object are abstact and should be defined by each
	 * implementing class.
	 *
	 * To resolve circular dependencies options to include parent and children are included
	 */
	public abstract SimpleResponse simpleResponse (Domain entity);
	public abstract DetailResponse detailResponse (Domain entity);

	/**
	 * operations to generate the domain class from the create and update objects are abstract
	 * and should be defined by each implementing class.
	 */
	public abstract Domain createDomain (CreateData data);
	public abstract Domain updateDomain (UpdateData data);

	/**
	 * operations to generate the domain class key
	 */
	public abstract DomainKey createDomainKey (AccessKey data);
}




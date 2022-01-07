package com.aoede.commons.base.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.commons.base.service.AbstractService;
import com.aoede.commons.base.transfer.AbstractCompositeResponse;
import com.aoede.commons.base.transfer.CompositeKey;

/**
 * Resource controller can be used in case the domain object differs
 * from the request and response objects
 *
 * Controller will convert the request objects to a domain class before
 * passing it to the service layer and additional convert the resulting domain classes
 * into the response objects before returning them as a result
 */
public abstract class AbstractCompositeResourceController<
	DomainKey,
	Domain extends AbstractDomain<DomainKey>,
	AccessKey extends CompositeKey,
	CreateData,
	UpdateData,
	SimpleResponse extends AbstractCompositeResponse <AccessKey>,
	DetailResponse extends AbstractCompositeResponse <AccessKey>,
	Service extends AbstractService<DomainKey, Domain>
> extends AbstractResourceController <DomainKey, Domain, AccessKey, CreateData, UpdateData, SimpleResponse, DetailResponse, Service> {

	@Autowired
	public AbstractCompositeResourceController(Service service) {
		super(service);
	}

}




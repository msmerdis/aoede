package com.aoede.commons.base.controller;

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
	K,
	D extends AbstractDomain<K>,
	A extends CompositeKey,
	C,
	U,
	R0 extends AbstractCompositeResponse <A>,
	R1 extends AbstractCompositeResponse <A>,
	S extends AbstractService<K, D>
> extends AbstractResourceController <K, D, A, C, U, R0, R1, S> {

	protected AbstractCompositeResourceController(S service) {
		super(service);
	}

}




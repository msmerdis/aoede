package com.aoede.commons.base.simplekey;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractResourceController;

@RestController
@RequestMapping ("/api/test/simplekey")
public class SimpleKeyController extends AbstractResourceController<
	Integer,
	SimpleKeyDomain,
	Integer,
	SimpleKeyCreateData,
	SimpleKeyUpdateData,
	SimpleKeySimpleResponse,
	SimpleKeyDetailResponse,
	SimpleKeyService
> {

	public SimpleKeyController(SimpleKeyService service) {
		super(service);
	}

	@Override
	public SimpleKeySimpleResponse simpleResponse(SimpleKeyDomain entity, boolean includeParent, boolean cascade) {
		SimpleKeySimpleResponse simpleKeySimpleResponse = new SimpleKeySimpleResponse ();

		simpleKeySimpleResponse.setId(entity.getId());
		simpleKeySimpleResponse.setValue(entity.getValue());

		return simpleKeySimpleResponse;
	}

	@Override
	public SimpleKeyDetailResponse detailResponse(SimpleKeyDomain entity, boolean includeParent, boolean cascade) {
		SimpleKeyDetailResponse simpleKeyDetailResponse = new SimpleKeyDetailResponse ();

		simpleKeyDetailResponse.setId(entity.getId());
		simpleKeyDetailResponse.setValue(entity.getValue());

		return simpleKeyDetailResponse;
	}

	@Override
	public SimpleKeyDomain createDomain(SimpleKeyCreateData data) {
		SimpleKeyDomain domain = new SimpleKeyDomain ();

		domain.setValue(data.getValue());

		return domain;
	}

	@Override
	public SimpleKeyDomain updateDomain(SimpleKeyUpdateData data) {
		SimpleKeyDomain domain = new SimpleKeyDomain ();

		domain.setValue(data.getValue());

		return domain;
	}

	@Override
	public Integer createDomainKey(Integer data) {
		return data;
	}

}




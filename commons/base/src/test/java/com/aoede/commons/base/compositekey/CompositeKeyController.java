package com.aoede.commons.base.compositekey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractCompositeResourceController;

@RestController
@RequestMapping ("/api/test/compositekey")
public class CompositeKeyController extends AbstractCompositeResourceController<
	CompositeKeyDomainKey,
	CompositeKeyDomain,
	CompositeKeyAccessData,
	CompositeKeyCreateData,
	CompositeKeyUpdateData,
	CompositeKeySimpleResponse,
	CompositeKeyDetailResponse,
	CompositeKeyService
> {

	@Autowired
	public CompositeKeyController(CompositeKeyService service) {
		super(service);
	}

	@Override
	public CompositeKeySimpleResponse simpleResponse(CompositeKeyDomain entity, boolean includeParent, boolean cascade) {
		CompositeKeySimpleResponse compositeKeySimpleResponse = new CompositeKeySimpleResponse ();

		compositeKeySimpleResponse.setId(createAccessData(entity.getId()));
		compositeKeySimpleResponse.setChildId(entity.getId().getChildId());
		compositeKeySimpleResponse.setParentId(entity.getId().getParentId());
		compositeKeySimpleResponse.setValue(entity.getValue());

		return compositeKeySimpleResponse;
	}

	@Override
	public CompositeKeyDetailResponse detailResponse(CompositeKeyDomain entity, boolean includeParent, boolean cascade) {
		CompositeKeyDetailResponse compositeKeyDetailResponse = new CompositeKeyDetailResponse ();

		compositeKeyDetailResponse.setId(createAccessData(entity.getId()));
		compositeKeyDetailResponse.setChildId(entity.getId().getChildId());
		compositeKeyDetailResponse.setParentId(entity.getId().getParentId());
		compositeKeyDetailResponse.setValue(entity.getValue());

		return compositeKeyDetailResponse;
	}

	@Override
	public CompositeKeyDomain createDomain(CompositeKeyCreateData data) {
		CompositeKeyDomain domain = new CompositeKeyDomain ();
		CompositeKeyDomainKey key = new CompositeKeyDomainKey ();

		key.setChildId(data.getChildId());
		key.setParentId(data.getParentId());

		domain.setId(key);
		domain.setValue(data.getValue());

		return domain;
	}

	@Override
	public CompositeKeyDomain updateDomain(CompositeKeyUpdateData data) {
		CompositeKeyDomain domain = new CompositeKeyDomain ();

		domain.setValue(data.getValue());

		return domain;
	}

	@Override
	public CompositeKeyDomainKey createDomainKey(CompositeKeyAccessData data) {
		CompositeKeyDomainKey compositeKeyDomainKey = new CompositeKeyDomainKey ();

		compositeKeyDomainKey.setChildId(data.getChildId());
		compositeKeyDomainKey.setParentId(data.getParentId());

		return compositeKeyDomainKey;
	}

	public CompositeKeyAccessData createAccessData(CompositeKeyDomainKey key) {
		CompositeKeyAccessData compositeKeyAccessData = new CompositeKeyAccessData ();

		compositeKeyAccessData.setChildId(key.getChildId());
		compositeKeyAccessData.setParentId(key.getParentId());

		return compositeKeyAccessData;
	}

}




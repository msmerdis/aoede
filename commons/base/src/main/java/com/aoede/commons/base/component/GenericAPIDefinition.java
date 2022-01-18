package com.aoede.commons.base.component;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

public interface GenericAPIDefinition<
	AccessKey,
	CreateData,
	UpdateData,
	SimpleResponse,
	DetailResponse
> {
	public List<SimpleResponse> search(String keyword) throws Exception;
	public DetailResponse get(final AccessKey id) throws Exception;
	public List<SimpleResponse> findAll() throws Exception;
	public DetailResponse create(final CreateData data) throws Exception;
	public void update(final AccessKey id, @Valid @RequestBody final UpdateData data) throws Exception;
	public void delete(final AccessKey id) throws Exception;
}




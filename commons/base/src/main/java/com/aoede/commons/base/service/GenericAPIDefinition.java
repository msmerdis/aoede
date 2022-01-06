package com.aoede.commons.base.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

public interface GenericAPIDefinition<
	AccessData,
	CreateData,
	UpdateData,
	SimpleResponse,
	DetailResponse
> {
	public List<SimpleResponse> search(String keyword) throws Exception;
	public DetailResponse get(final AccessData id) throws Exception;
	public List<SimpleResponse> findAll() throws Exception;
	public DetailResponse create(final CreateData data) throws Exception;
	public void update(final AccessData id, @Valid @RequestBody final UpdateData data) throws Exception;
	public void delete(final AccessData id) throws Exception;
}




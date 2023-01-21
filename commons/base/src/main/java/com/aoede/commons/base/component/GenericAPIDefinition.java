package com.aoede.commons.base.component;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

public interface GenericAPIDefinition<A, C, U, R0, R1> {
	public List<R0> search(String keyword) throws Exception;
	public R1 get(final A id) throws Exception;
	public List<R0> findAll() throws Exception;
	public R1 create(final C data) throws Exception;
	public void update(final A id, @Valid @RequestBody final U data) throws Exception;
	public void delete(final A id) throws Exception;
}




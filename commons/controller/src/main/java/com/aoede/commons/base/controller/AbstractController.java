package com.aoede.commons.base.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.component.GenericAPIDefinition;
import com.aoede.commons.base.domain.AbstractDomain;

/**
 * Definition of basic CRUD operations for domain objects
 *
 * Assume that the data and response objects can be differ from the domain object itself
 */
public abstract class AbstractController<K, D extends AbstractDomain<K>, A, C, U, R0, R1> extends BaseComponent implements GenericAPIDefinition<A, C, U, R0, R1> {

	@GetMapping("/search")
	@ResponseStatus(HttpStatus.OK)
	public abstract List<R0> search(@RequestHeader("X-Search-Terms") String keyword) throws Exception;

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public abstract R1 get(@PathVariable("id") final A id) throws Exception;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public abstract List<R0> findAll() throws Exception;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public abstract R1 create(@Valid @RequestBody final C data) throws Exception;

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public abstract void update(@PathVariable("id") final A id, @Valid @RequestBody final U data) throws Exception;

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public abstract void delete(@PathVariable("id") final A id) throws Exception;
}




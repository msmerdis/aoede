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
import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.commons.base.service.AbstractService;
import com.aoede.commons.base.service.GenericAPIDefinition;

/**
 * Definition of basic CRUD operations for domain objects
 *
 * Assume that the data and response objects can be differ from the domain object itself
 */
public abstract class AbstractController<
	DomainKey,
	Domain extends AbstractDomain<DomainKey>,
	AccessData,
	CreateData,
	UpdateData,
	SimpleResponse,
	DetailResponse,
	Service extends AbstractService<DomainKey, Domain>
> extends BaseComponent implements GenericAPIDefinition<AccessData, CreateData, UpdateData, SimpleResponse, DetailResponse> {

	@GetMapping("/search")
	@ResponseStatus(HttpStatus.OK)
	abstract public List<SimpleResponse> search(@RequestHeader("X-Search-Terms") String keyword) throws Exception;

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	abstract public DetailResponse get(@PathVariable("id") final AccessData id) throws Exception;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	abstract public List<SimpleResponse> findAll() throws Exception;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	abstract public DetailResponse create(@Valid @RequestBody final CreateData data) throws Exception;

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	abstract public void update(@PathVariable("id") final AccessData id, @Valid @RequestBody final UpdateData data) throws Exception;

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	abstract public void delete(@PathVariable("id") final AccessData id) throws Exception;
}




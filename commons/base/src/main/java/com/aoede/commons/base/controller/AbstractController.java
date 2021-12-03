package com.aoede.commons.base.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

public abstract class AbstractController<
	Key,
	Domain extends AbstractDomain<Key>,
	CreateRequest,
	UpdateRequest,
	SimpleResponse,
	DetailResponse,
	Service extends AbstractService<Key, Domain>
> extends BaseComponent {

	final protected Service service;

	@Autowired
	public AbstractController (Service service) {
		this.service = service;
	}

	@GetMapping("/search")
	@ResponseStatus(HttpStatus.OK)
	public List<SimpleResponse> search(@RequestHeader("X-Search-Terms") String keyword) throws Exception {
		return service.freeTextSearch(keyword).stream().map(e -> simpleResponse(e)).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public DetailResponse get(@PathVariable("id") final Key id) throws Exception {
		return detailResponse(service.find(id));
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<SimpleResponse> findAll() throws Exception {
		return service.findAll().stream().map(e -> simpleResponse(e)).collect(Collectors.toList());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DetailResponse create(@Valid @RequestBody CreateRequest request) throws Exception {
		return detailResponse(service.create(createRequest(request)));
	}

	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@Valid @RequestBody final UpdateRequest request) throws Exception {
		service.update(updateRequest(request));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") final Key id) throws Exception {
		service.delete(id);
	}

	abstract protected SimpleResponse simpleResponse (Domain entity);
	abstract protected DetailResponse detailResponse (Domain entity);

	abstract protected Domain createRequest (CreateRequest request);
	abstract protected Domain updateRequest (UpdateRequest request);
}




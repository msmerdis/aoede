package com.aoede.modules.music.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractDomainController;
import com.aoede.commons.base.exceptions.BadRequestException;
import com.aoede.modules.music.domain.Tempo;
import com.aoede.modules.music.service.TempoService;

@RestController
@RequestMapping ("/api/tempo")
public class TempoController extends AbstractDomainController<String, Tempo, TempoService> {

	public TempoController(TempoService service) {
		super(service);
	}

	@Override
	public Tempo create(@Valid @RequestBody final Tempo domain) throws Exception {
		throw new BadRequestException("Time signatures cannot be created");
	}

	@Override
	public void update(@PathVariable("id") final String id, @Valid @RequestBody final Tempo domain) throws Exception {
		throw new BadRequestException("Time signatures cannot be updated");
	}

	@Override
	public void delete(@PathVariable("id") final String id) throws Exception {
		throw new BadRequestException("Time signatures cannot be deleted");
	}

}




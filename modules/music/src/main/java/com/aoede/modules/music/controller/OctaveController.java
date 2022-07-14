package com.aoede.modules.music.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractDomainController;
import com.aoede.commons.base.exceptions.BadRequestException;
import com.aoede.modules.music.domain.Octave;
import com.aoede.modules.music.service.OctaveService;

@RestController
@RequestMapping ("/api/octave")
public class OctaveController extends AbstractDomainController<Integer, Octave, OctaveService> {

	public OctaveController(OctaveService service) {
		super(service);
	}

	@Override
	public Octave create(@Valid @RequestBody final Octave domain) throws Exception {
		throw new BadRequestException("Octaves cannot be created");
	}

	@Override
	public void update(@PathVariable("id") final Integer id, @Valid @RequestBody final Octave domain) throws Exception {
		throw new BadRequestException("Octaves cannot be updated");
	}

	@Override
	public void delete(@PathVariable("id") final Integer id) throws Exception {
		throw new BadRequestException("Octaves cannot be deleted");
	}

}




package com.aoede.modules.music.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractDomainController;
import com.aoede.commons.base.exceptions.BadRequestException;
import com.aoede.modules.music.domain.Clef;
import com.aoede.modules.music.service.ClefService;

@RestController
@RequestMapping ("/api/clef")
public class ClefController extends AbstractDomainController<String, Clef, ClefService> {

	public ClefController(ClefService service) {
		super(service);
	}

	@Override
	public Clef create(@Valid @RequestBody final Clef domain) throws Exception {
		throw new BadRequestException("Clefs cannot be created");
	}

	@Override
	public void update(@PathVariable("id") final String id, @Valid @RequestBody final Clef domain) throws Exception {
		throw new BadRequestException("Clefs cannot be updated");
	}

	@Override
	public void delete(@PathVariable("id") final String id) throws Exception {
		throw new BadRequestException("Clefs cannot be deleted");
	}
}




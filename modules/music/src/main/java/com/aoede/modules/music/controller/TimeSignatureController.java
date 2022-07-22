package com.aoede.modules.music.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractDomainController;
import com.aoede.commons.base.exceptions.BadRequestException;
import com.aoede.modules.music.domain.Fraction;
import com.aoede.modules.music.domain.TimeSignature;
import com.aoede.modules.music.service.TimeSignatureService;

@RestController
@RequestMapping ("/api/time_signature")
public class TimeSignatureController extends AbstractDomainController<Fraction, TimeSignature, TimeSignatureService> {

	public TimeSignatureController(TimeSignatureService service) {
		super(service);
	}

	@Override
	public TimeSignature create(@Valid @RequestBody final TimeSignature domain) throws Exception {
		throw new BadRequestException("Time signatures cannot be created");
	}

	@Override
	public void update(@PathVariable("id") final Fraction id, @Valid @RequestBody final TimeSignature domain) throws Exception {
		throw new BadRequestException("Time signatures cannot be updated");
	}

	@Override
	public void delete(@PathVariable("id") final Fraction id) throws Exception {
		throw new BadRequestException("Time signatures cannot be deleted");
	}

}




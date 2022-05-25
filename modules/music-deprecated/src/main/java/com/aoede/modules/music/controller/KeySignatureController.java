package com.aoede.modules.music.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractDomainController;
import com.aoede.commons.base.exceptions.BadRequestException;
import com.aoede.modules.music.domain.KeySignature;
import com.aoede.modules.music.service.KeySignatureService;

@RestController
@RequestMapping ("/api/key_signature")
public class KeySignatureController extends AbstractDomainController<Short, KeySignature, KeySignatureService> {

	public KeySignatureController(KeySignatureService service) {
		super(service);
	}

	@Override
	public KeySignature create(@Valid @RequestBody final KeySignature domain) throws Exception {
		throw new BadRequestException("Key signatures cannot be created");
	}

	@Override
	public void update(@PathVariable("id") final Short id, @Valid @RequestBody final KeySignature domain) throws Exception {
		throw new BadRequestException("Key signatures cannot be updated");
	}

	@Override
	public void delete(@PathVariable("id") final Short id) throws Exception {
		throw new BadRequestException("Key signatures cannot be deleted");
	}
}




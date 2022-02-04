package com.aoede.modules.music.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.cucumber.service.AbstractTestServiceImpl;

@Component
public class KeySignatureTestServiceImpl extends AbstractTestServiceImpl implements KeySignatureTestService {
	@Override
	public String getName() {
		return "keySignature";
	}

	@Override
	public String getPath() {
		return "/api/key_signature";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

}




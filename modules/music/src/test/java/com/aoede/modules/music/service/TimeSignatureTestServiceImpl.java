package com.aoede.modules.music.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.cucumber.service.AbstractTestServiceImpl;

@Component
public class TimeSignatureTestServiceImpl extends AbstractTestServiceImpl implements TimeSignatureTestService {
	@Override
	public String getName() {
		return "timeSignature";
	}

	@Override
	public String getPath() {
		return "/api/time_signature";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

}




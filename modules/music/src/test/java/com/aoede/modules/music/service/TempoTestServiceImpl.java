package com.aoede.modules.music.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.cucumber.service.AbstractTestServiceImpl;

@Component
public class TempoTestServiceImpl extends AbstractTestServiceImpl implements TempoTestService {
	@Override
	public String getName() {
		return "tempo";
	}

	@Override
	public String getPath() {
		return "/api/tempo";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

}




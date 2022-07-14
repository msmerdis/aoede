package com.aoede.modules.music.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.cucumber.service.AbstractTestServiceImpl;

@Component
public class OctaveTestServiceImpl extends AbstractTestServiceImpl implements OctaveTestService {
	@Override
	public String getName() {
		return "octave";
	}

	@Override
	public String getPath() {
		return "/api/octave";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

}




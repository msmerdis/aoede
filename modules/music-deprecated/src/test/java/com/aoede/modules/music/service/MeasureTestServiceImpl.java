package com.aoede.modules.music.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.cucumber.service.AbstractTestServiceImpl;

@Component
public class MeasureTestServiceImpl extends AbstractTestServiceImpl implements MeasureTestService {

	@Override
	public String getName() {
		return "measure";
	}

	@Override
	public String getPath() {
		return "/api/measure";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

}




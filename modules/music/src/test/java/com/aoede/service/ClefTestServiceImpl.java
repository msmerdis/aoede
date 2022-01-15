package com.aoede.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestServiceImpl;

@Component
public class ClefTestServiceImpl extends AbstractTestServiceImpl implements ClefTestService {
	@Override
	public String getName() {
		return "clef";
	}

	@Override
	public String getPath() {
		return "/api/clef";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

}




package com.aoede.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestService;

@Component
public class ClefTestService extends AbstractTestService {

	@Override
	public String getName() {
		return "clef";
	}

	@Override
	public String getPath() {
		return "/api/clef";
	}

}




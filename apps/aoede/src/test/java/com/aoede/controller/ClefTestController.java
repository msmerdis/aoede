package com.aoede.controller;

import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestService;

@Component
public class ClefTestController extends AbstractTestService {

	@Override
	public String getName() {
		return "clef";
	}

	@Override
	public String getPath() {
		return "/api/clef";
	}

}




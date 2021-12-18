package com.aoede.controller;

import org.springframework.stereotype.Component;

import com.aoede.commons.AbstractTestController;

@Component
public class ClefTestController extends AbstractTestController {

	@Override
	public String getName() {
		return "clef";
	}

	@Override
	public String getPath() {
		return "/api/clef";
	}

}




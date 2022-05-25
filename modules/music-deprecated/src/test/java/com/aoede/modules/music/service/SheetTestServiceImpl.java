package com.aoede.modules.music.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.cucumber.service.AbstractTestServiceImpl;

@Component
public class SheetTestServiceImpl extends AbstractTestServiceImpl implements SheetTestService {
	@Override
	public String getName() {
		return "sheet";
	}

	@Override
	public String getPath() {
		return "/api/sheet";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

}




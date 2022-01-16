package com.aoede.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestServiceImpl;

@Component
public class SectionTestServiceImpl extends AbstractTestServiceImpl implements SectionTestService {

	@Override
	public String getName() {
		return "section";
	}

	@Override
	public String getPath() {
		return "/api/section";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

}




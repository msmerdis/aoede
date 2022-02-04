package com.aoede.modules.music.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aoede.commons.cucumber.service.AbstractTestServiceImpl;

@Component
public class MeasureTestServiceImpl extends AbstractTestServiceImpl implements MeasureTestService {
	@Autowired
	SectionTestService sectionTestService;

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




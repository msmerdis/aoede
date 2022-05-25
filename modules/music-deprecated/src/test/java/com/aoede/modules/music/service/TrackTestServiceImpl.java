package com.aoede.modules.music.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.cucumber.service.AbstractTestServiceImpl;

@Component
public class TrackTestServiceImpl extends AbstractTestServiceImpl implements TrackTestService {

	@Override
	public String getName() {
		return "track";
	}

	@Override
	public String getPath() {
		return "/api/track";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

}




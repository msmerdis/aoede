package com.aoede.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestServiceImpl;

@Component
public class NoteTestServiceImpl extends AbstractTestServiceImpl implements NoteTestService {

	@Override
	public String getName() {
		return "note";
	}

	@Override
	public String getPath() {
		return "/api/note";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

}




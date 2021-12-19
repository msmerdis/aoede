package com.aoede.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestService;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

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

	@Override
	public String getKeyName() {
		return "id";
	}

	@Override
	protected JsonPrimitive getPrimitive(String name, String value) {
		return new JsonPrimitive(value);
	}

	@Override
	public void createDefaultBody(JsonObject obj) {}

}




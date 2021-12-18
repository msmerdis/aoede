package com.aoede.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestService;
import com.google.gson.JsonPrimitive;

@Component
public class SheetTestService extends AbstractTestService {

	@Override
	public String getName() {
		return "sheet";
	}

	@Override
	public String getPath() {
		return "/api/sheet";
	}

	@Override
	protected JsonPrimitive getPrimitive(String name, String value) {
		switch (name) {
		case "id":
			return new JsonPrimitive(Long.parseLong(value));
		default:
			return new JsonPrimitive(value);
		}
	}
}




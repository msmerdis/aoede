package com.aoede.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestService;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

@Component
public class SheetTestService extends AbstractTestService {
	private static final long serialVersionUID = 1L;

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

	@Override
	protected void addJsonElement (JsonObject obj, String name, String value) {
		switch (name) {
		case "id":
			obj.add(name, new JsonPrimitive(Long.parseLong(value)));
			break;
		default:
			obj.add(name, new JsonPrimitive(value));
		}
	}

	@Override
	public void createDefaultBody(JsonObject obj) {
		obj.add("name", new JsonPrimitive("sheet_" + randomString(18)));
	}
}




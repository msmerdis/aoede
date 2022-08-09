package com.aoede.commons.cucumber.service.generators;

import com.aoede.commons.cucumber.service.JsonElementGenerator;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class BooleanGenerator implements JsonElementGenerator {
	@Override
	public JsonElement generate(String value) {
		return new JsonPrimitive(Boolean.parseBoolean(value));
	}
}




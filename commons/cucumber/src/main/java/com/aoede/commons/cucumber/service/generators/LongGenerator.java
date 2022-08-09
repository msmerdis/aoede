package com.aoede.commons.cucumber.service.generators;

import com.aoede.commons.cucumber.service.JsonElementGenerator;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class LongGenerator implements JsonElementGenerator {

	@Override
	public JsonElement generate(String value) {
		return new JsonPrimitive(Integer.parseInt(value));
	}

}




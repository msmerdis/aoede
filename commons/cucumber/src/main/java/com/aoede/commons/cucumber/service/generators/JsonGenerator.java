package com.aoede.commons.cucumber.service.generators;

import static org.junit.Assert.assertTrue;

import com.aoede.commons.cucumber.service.JsonElementGenerator;
import com.aoede.commons.cucumber.service.JsonService;
import com.google.gson.JsonElement;

public class JsonGenerator implements JsonElementGenerator {
	private JsonService jsonService;

	public JsonGenerator (JsonService jsonService) {
		this.jsonService = jsonService;
	}

	@Override
	public JsonElement generate(String value) {
		assertTrue("json " + value + " not found", jsonService.containsKey(value));
		return jsonService.get(value);
	}
}




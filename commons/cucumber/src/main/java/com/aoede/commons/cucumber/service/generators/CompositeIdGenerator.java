package com.aoede.commons.cucumber.service.generators;

import static org.junit.Assert.assertTrue;

import com.aoede.commons.cucumber.service.JsonService;
import com.google.gson.JsonElement;

public class CompositeIdGenerator extends JsonGenerator {
	public CompositeIdGenerator (JsonService jsonService) {
		super(jsonService);
	}

	@Override
	public JsonElement generate(String value) {
		JsonElement element = super.generate(value);

		assertTrue("json " + value + " is not primitive", element.isJsonPrimitive());
		assertTrue("json " + value + " is not string", element.getAsJsonPrimitive().isString());

		return element;
	}
}




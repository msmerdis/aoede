package com.aoede.commons.cucumber.service.generators;

import com.aoede.commons.cucumber.service.JsonElementGenerator;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

public class NullGenerator implements JsonElementGenerator {
	@Override
	public JsonElement generate(String value) {
		return JsonNull.INSTANCE;
	}
}




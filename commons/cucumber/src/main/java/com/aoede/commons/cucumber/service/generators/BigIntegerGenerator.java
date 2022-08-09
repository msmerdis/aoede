package com.aoede.commons.cucumber.service.generators;

import java.math.BigInteger;

import com.aoede.commons.cucumber.service.JsonElementGenerator;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class BigIntegerGenerator implements JsonElementGenerator {
	@Override
	public JsonElement generate(String value) {
		return new JsonPrimitive(new BigInteger(value));
	}
}




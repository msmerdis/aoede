package com.aoede.commons.cucumber.service.generators;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import com.aoede.commons.cucumber.service.JsonElementGenerator;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class FractionGenerator implements JsonElementGenerator {
	@Override
	public JsonElement generate(String value) {
		String[] parts = value.split("/");

		assertEquals("fraction must have two parts", 2, parts.length);

		return generate (new BigInteger(parts[0].trim()), new BigInteger(parts[1].trim()));
	}

	public JsonObject generate (BigInteger num, BigInteger den) {
		JsonObject fraction = new JsonObject ();

		fraction.add( "numerator" , new JsonPrimitive(num));
		fraction.add("denominator", new JsonPrimitive(den));

		return fraction;
	}

}




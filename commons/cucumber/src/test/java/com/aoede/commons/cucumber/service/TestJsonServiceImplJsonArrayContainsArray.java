package com.aoede.commons.cucumber.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.aoede.JsonServiceImplTestCaseSetup;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class TestJsonServiceImplJsonArrayContainsArray extends JsonServiceImplTestCaseSetup {

	@Test
	public void verifyJsonArrayContainsArraySuccess () throws Exception {
		assertTrue ("json is not matched correctly", uut().jsonArrayContainsArray(buildDefaultArray(), buildArray(1)));
	}

	@Test
	public void verifyJsonArrayContainsArrayFailure () throws Exception {
		assertFalse ("json is not matched correctly", uut().jsonArrayContainsArray(buildDefaultArray(), buildArray(17)));
	}

	@Test
	public void verifyJsonArrayContainsEmptyArray () throws Exception {
		assertTrue ("json is not matched correctly", uut().jsonArrayContainsArray(buildDefaultArray(), new JsonArray ()));
	}

	@Test
	public void verifyJsonEmptyArrayContainsEmptyArray () throws Exception {
		JsonArray array = new JsonArray ();

		array.add(buildArray(0));

		assertTrue ("json is not matched correctly", uut().jsonArrayContainsArray(array, buildArray(0)));
	}

	private JsonArray buildDefaultArray () {
		JsonArray array = new JsonArray ();

		for (int i = 1; i <= 10; i += 1)
			array.add(buildArray(i));

		return array;
	}

	private JsonArray buildArray (int value) {
		JsonArray array = new JsonArray ();

		array.add(buildObject(value));

		return array;
	}

	private JsonObject buildObject (int value) {
		JsonObject object = new JsonObject ();

		object.add("id", new JsonPrimitive(value));
		object.add("name", new JsonPrimitive("name" + value));
		object.add("value", new JsonPrimitive("value" + value));

		return object;
	}
}




package com.aoede.commons.cucumber.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.aoede.JsonObjectServiceImplTestCaseSetup;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class TestJsonObjectServiceImplJsonArrayMatches extends JsonObjectServiceImplTestCaseSetup {

	@Test
	public void verifyJsonArrayMatchPrimitive () throws Exception {
		JsonArray expected = new JsonArray ();

		expected.add(new JsonPrimitive(3));
		expected.add(new JsonPrimitive(7));

		JsonArray actual = new JsonArray ();

		actual.add(new JsonPrimitive(1));
		actual.add(new JsonPrimitive(3));
		actual.add(new JsonPrimitive(5));
		actual.add(new JsonPrimitive(7));
		actual.add(new JsonPrimitive(9));

		assertTrue ("json is not matched correctly", uut().jsonArrayMatches(actual, expected));
		assertFalse ("json is not matched correctly", uut().jsonArrayMatches(expected, actual));
	}

	@Test
	public void verifyJsonArrayDontMatchPrimitive () throws Exception {
		JsonArray expected = new JsonArray ();

		expected.add(new JsonPrimitive(3));
		expected.add(new JsonPrimitive(6));

		JsonArray actual = new JsonArray ();

		actual.add(new JsonPrimitive(1));
		actual.add(new JsonPrimitive(3));
		actual.add(new JsonPrimitive(5));
		actual.add(new JsonPrimitive(7));
		actual.add(new JsonPrimitive(9));

		assertFalse ("json is not matched correctly", uut().jsonArrayMatches(actual, expected));
		assertFalse ("json is not matched correctly", uut().jsonArrayMatches(expected, actual));
	}

	@Test
	public void verifyJsonArrayMatchObject () throws Exception {
		JsonArray expected = new JsonArray ();

		expected.add(buildObject(3));
		expected.add(buildObject(7));

		JsonArray actual = new JsonArray ();

		actual.add(buildObject(1));
		actual.add(buildObject(3));
		actual.add(buildObject(5));
		actual.add(buildObject(7));
		actual.add(buildObject(9));

		assertTrue ("json is not matched correctly", uut().jsonArrayMatches(actual, expected));
		assertFalse ("json is not matched correctly", uut().jsonArrayMatches(expected, actual));
	}

	@Test
	public void verifyJsonArrayDontMatchObject () throws Exception {
		JsonArray expected = new JsonArray ();

		expected.add(buildObject(3));
		expected.add(buildObject(8));

		JsonArray actual = new JsonArray ();

		actual.add(buildObject(1));
		actual.add(buildObject(3));
		actual.add(buildObject(5));
		actual.add(buildObject(7));
		actual.add(buildObject(9));

		assertFalse ("json is not matched correctly", uut().jsonArrayMatches(actual, expected));
		assertFalse ("json is not matched correctly", uut().jsonArrayMatches(expected, actual));
	}

	@Test
	public void verifyJsonArrayMatchObjectMixedTypes () throws Exception {
		JsonArray expected = new JsonArray ();

		expected.add(buildObject(3));

		JsonArray actual = new JsonArray ();

		actual.add(new JsonPrimitive(1));
		actual.add(buildObject(3));
		actual.add(JsonNull.INSTANCE);
		actual.add(new JsonArray());

		assertTrue ("json is not matched correctly", uut().jsonArrayMatches(actual, expected));
		assertFalse ("json is not matched correctly", uut().jsonArrayMatches(expected, actual));
	}

	private JsonObject buildObject (int value) {
		JsonObject object = new JsonObject ();

		object.add("id", new JsonPrimitive(value));

		return object;
	}

}




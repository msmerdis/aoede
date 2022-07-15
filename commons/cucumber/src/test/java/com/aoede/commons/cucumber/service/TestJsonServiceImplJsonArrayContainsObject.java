package com.aoede.commons.cucumber.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.aoede.JsonServiceImplTestCaseSetup;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class TestJsonServiceImplJsonArrayContainsObject extends JsonServiceImplTestCaseSetup {

	@Test
	public void verifyJsonArrayContainsObjectWithId () throws Exception {
		assertTrue ("json is not matched correctly", uut().jsonArrayContainsObject(buildDefaultArray(), "id", "int", "3"));
	}

	@Test
	public void verifyJsonArrayContainsObjectWithName () throws Exception {
		assertTrue ("json is not matched correctly", uut().jsonArrayContainsObject(buildDefaultArray(), "name", "string", "name1"));
	}

	@Test
	public void verifyJsonArrayContainsObjectWithValue () throws Exception {
		assertFalse("json is not matched correctly", uut().jsonArrayContainsObject(buildDefaultArray(), "value", "string", "value123"));
	}

	@Test
	public void verifyJsonArrayContainsObjectKeyNotFound () throws Exception {
		assertFalse("json is not matched correctly", uut().jsonArrayContainsObject(buildDefaultArray(), "other", "int", "2"));
	}

	@Test
	public void verifyJsonArrayContainsObjectWithElementPrimitiveSuccess () throws Exception {
		assertTrue ("json is not matched correctly", uut().jsonArrayContainsObjectWithElement(buildDefaultArray(), "id", new JsonPrimitive(4)));
	}

	@Test
	public void verifyJsonArrayContainsObjectWithElementPrimitiveFailureKey () throws Exception {
		assertFalse ("json is not matched correctly", uut().jsonArrayContainsObjectWithElement(buildDefaultArray(), "other", new JsonPrimitive(1)));
	}

	@Test
	public void verifyJsonArrayContainsObjectWithElementPrimitiveFailureValue () throws Exception {
		assertFalse ("json is not matched correctly", uut().jsonArrayContainsObjectWithElement(buildDefaultArray(), "id", new JsonPrimitive(100)));
	}

	@Test
	public void verifyJsonArrayContainsObjectId () throws Exception {
		JsonObject object = new JsonObject ();

		object.add("id", new JsonPrimitive(10));

		assertTrue ("json is not matched correctly", uut().jsonArrayContainsObject(buildDefaultArray(), object));
	}

	@Test
	public void verifyJsonArrayContainsObjectName () throws Exception {
		JsonObject object = new JsonObject ();

		object.add("name", new JsonPrimitive("name4"));

		assertTrue ("json is not matched correctly", uut().jsonArrayContainsObject(buildDefaultArray(), object));
	}

	@Test
	public void verifyJsonArrayContainsObjectWrongValue () throws Exception {
		JsonObject object = new JsonObject ();

		object.add("name", new JsonPrimitive("value5"));

		assertFalse("json is not matched correctly", uut().jsonArrayContainsObject(buildDefaultArray(), object));
	}

	private JsonArray buildDefaultArray () {
		JsonArray array = new JsonArray ();

		for (int i = 1; i <= 10; i += 1)
			array.add(buildObject(i));

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




package com.aoede.commons.cucumber.stepdefs;

import org.junit.Test;

import com.aoede.DataAssertionStepDefinitionsTestCaseSetup;
import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.service.JsonServiceImpl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class TestDataAssertionStepDefinitions extends DataAssertionStepDefinitionsTestCaseSetup {

	private JsonServiceImpl jsonServiceImpl;

	@Override
	protected DataAssertionStepDefinitions uut () throws Exception {
		var uut = super.uut();

		// use actual json object service for the test
		jsonServiceImpl = new JsonServiceImpl(null);
		setField ((BaseStepDefinition)uut, "jsonService", jsonServiceImpl);

		return uut;
	}

	private JsonObject generateJsonObject (String key, String value) {
		JsonObject obj = new JsonObject();

		obj.add(key, new JsonPrimitive(value));

		return obj;
	}

	@Test
	public void verifyJsonObjectContainsElementSuccess() throws Exception {
		var uut = uut();

		jsonServiceImpl.put("json", generateJsonObject("key", "value"));

		uut.jsonObjectContainsElement("json", "key");
	}

	@Test
	public void verifyJsonObjectMatchesElementSuccess() throws Exception {
		var uut = uut();

		jsonServiceImpl.put("json", generateJsonObject("key", "value"));

		uut.jsonObjectMatchesElement("json", "key", "value", "string");
	}

	private JsonArray generateJsonArray (String... values) {
		JsonArray arr = new JsonArray();

		for(String value : values) {
			arr.add(value);
		}

		return arr;
	}

	@Test
	public void verifyJsonArrayContainsCountSuccess() throws Exception {
		var uut = uut();

		jsonServiceImpl.put("json", generateJsonArray("key1", "value1", "key2", "value2"));

		uut.jsonArrayContainsCount("json", 4);
	}

}




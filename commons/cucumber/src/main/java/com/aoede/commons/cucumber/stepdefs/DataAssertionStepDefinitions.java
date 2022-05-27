package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DataAssertionStepDefinitions extends BaseStepDefinition {

	public DataAssertionStepDefinitions (
		ServerProperties serverProperties,
		AbstractTestServiceDiscoveryService services,
		TestCaseIdTrackerService testCaseIdTrackerService,
		JsonService jsonService,
		DataTableService dataTableService
	) {
		super (
			serverProperties,
			services,
			testCaseIdTrackerService,
			jsonService,
			dataTableService
		);
	}

	@When("json object {string} contains {string}")
	public void jsonObjectContainsElement(String json, String element) {
		JsonObject obj = jsonService.getObject(json);

		assertTrue("", obj.has(element));
	}

	@When("{string} json object's {string} element matches {string} as {string}")
	public void jsonObjectMatchesElement(String json, String element, String value, String type) {
		JsonObject obj = jsonService.getObject(json);

		assertTrue("object does not have such element", obj.has(element));

		assertEquals("element does not match", obj.get(element), jsonService.generateJsonElement(type, value));
	}

	@When("json array {string} contains {int} items")
	public void jsonArrayContainsCount(String json, int count) {
		JsonArray obj = jsonService.getArray(json);

		assertEquals("", obj.size(), count);
	}

	@Then("json array {string} contains {string} objects")
	public void jsonArrayContains (String json, String dataTableName, DataTable data) {
		// generate a json array using a previously stored template
		JsonArray array = jsonService.generateJsonArray(
			dataTableService.get(dataTableName), data);

		assertNotNull("could not generate json array", array);

		assertTrue(
			"json array does not match objects",
			jsonService.jsonArrayMatches(
				jsonService.getArray(json),
				array
			)
		);
	}

	@Then("{string} json array contains object with {string} element matching {string} as {string}")
	public void jsonArrayContainsObjectWithElement (String json, String element, String value, String type) {
		assertTrue(matchJsonArrayContainsObjectWithElement(json, element, type, value));
	}

	@Then("{string} json array does not contain object with {string} element matching {string} as {string}")
	public void jsonArrayDoesNotContainObjectWithElement (String json, String element, String value, String type) {
		assertFalse(matchJsonArrayContainsObjectWithElement(json, element, type, value));
	}

	private boolean matchJsonArrayContainsObjectWithElement(String json, String element, String type, String value) {
		JsonObject obj = new JsonObject();

		obj.add(element, jsonService.generateJsonElement(type, value));

		return jsonService.jsonArrayContainsObject(
			jsonService.getArray(json),
			obj
		);
	}

	@When("{string} json matches {string} json")
	public void jsonMatchesJson(String json1, String json2) {
		JsonElement element1 = jsonService.get(json1);
		JsonElement element2 = jsonService.get(json2);

		assertNotNull(json1 + " was not found", element1);
		assertNotNull(json2 + " was not found", element2);

		jsonMatchesJson(element1, element2);
	}

	private void jsonMatchesJson(JsonElement element1, JsonElement element2) {
		if (
			element1.isJsonNull() &&
			element2.isJsonNull()
		) {
			// dont have to do any further checks elements are equal
		}

		else if (
			element1.isJsonPrimitive() &&
			element2.isJsonPrimitive()
		) {
			assertEquals(
				"json primitives do not match",
				element1,
				element2
			);
		}

		else if (
			element1.isJsonObject() &&
			element2.isJsonObject()
		) {
			assertTrue(
				"json objects do not match",
				jsonService.jsonObjectMatches(
					element1.getAsJsonObject(),
					element2.getAsJsonObject()
				)
			);
		}

		else if (
			element1.isJsonArray() &&
			element2.isJsonArray()
		) {
			assertTrue(
				"json arrays do not match",
				jsonService.jsonArrayMatches(
					element1.getAsJsonArray(),
					element2.getAsJsonArray()
				)
			);
		}

		else {
			assertTrue(
				"json elements are not of the same type",
				false
			);
		}
	}

}




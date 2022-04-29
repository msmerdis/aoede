package com.aoede.commons.base.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.ResponseResults;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.CompositeIdService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.JsonObjectService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ExceptionTestDefinitions extends BaseStepDefinition {

	public ExceptionTestDefinitions (
		ServerProperties serverProperties,
		AbstractTestServiceDiscoveryService services,
		TestCaseIdTrackerService testCaseIdTrackerService,
		CompositeIdService compositeIdService,
		JsonObjectService jsonObjectService,
		DataTableService dataTableService
	) {
		super (
			serverProperties,
			services,
			testCaseIdTrackerService,
			compositeIdService,
			jsonObjectService,
			dataTableService
		);
	}

	private ResponseResults latestResults = new ResponseResults ();

	/**
	 * Requests
	 */

	@When("testing {string} error")
	public void getErrorRequest(String path) {
		latestResults = executeGet ("/test/error/" + path, defaultHeaders());
	}

	@When("testing {string} error info")
	public void getErrorInfoRequest(String path) {
		latestResults = executeGet ("/test/error/info/" + path, defaultHeaders());
	}

	@When("testing {string} validation info")
	public void getErrorValidationRequest(String path) {
		latestResults = executeGet ("/test/error/validation/" + path, defaultHeaders());
	}

	/**
	 * assertions
	 */

	@Then("the response has a status code of {int} and matches")
	public void verifyResponse (int code, DataTable data) {
		assertEquals(code, latestResults.status.value());

		JsonObject object = JsonParser.parseString(latestResults.body).getAsJsonObject();

		for (var row : data.asLists()) {
			assertTrue (
				"response does not have element " + row.get(0),
				object.has(row.get(0))
			);
			assertTrue (
				"response element " + row.get(0) + " does not match " + row.get(1),
				object.get(row.get(0)).getAsString().equals(row.get(1))
			);
		}
	}

	@Then("contains info array with severity {string} and text {string}")
	public void verifyInfoResponse (String type, String text) {
		boolean match = false;
		JsonObject object = JsonParser.parseString(latestResults.body).getAsJsonObject();

		assertTrue (
			"response does not have element info",
			object.has("info")
		);

		assertTrue (
			"info element is not an array",
			object.get("info").isJsonArray()
		);

		for (var element : object.get("info").getAsJsonArray()) {

			assertTrue (
				"info array does not contain objects",
				element.isJsonObject()
			);

			var inner = element.getAsJsonObject();

			assertTrue (
				"info array element does not contain type",
				inner.has("type") && inner.get("type").isJsonPrimitive()
			);

			assertTrue (
				"info array element does not contain text",
				inner.has("text") && inner.get("type").isJsonPrimitive()
			);

			if (
				inner.get("type").getAsString().equals(type) &&
				inner.get("text").getAsString().equals(text)
			) {
				match = true;
			}
		}

		assertTrue (
			"info element was not found",
			match
		);
	}

	@Then("contains validation array with field {string}, value {string} and error {string}")
	public void verifyValidationResponse (String field, String value, String error) {
		boolean match = false;
		JsonObject object = JsonParser.parseString(latestResults.body).getAsJsonObject();

		assertTrue (
			"response does not have element validations",
			object.has("validations")
		);

		assertTrue (
			"validations element is not an array",
			object.get("validations").isJsonArray()
		);

		for (var element : object.get("validations").getAsJsonArray()) {

			assertTrue (
				"validations array does not contain objects",
				element.isJsonObject()
			);

			var inner = element.getAsJsonObject();

			assertTrue (
				"validations array element does not contain field",
				inner.has("field") && inner.get("field").isJsonPrimitive()
			);

			assertTrue (
				"validations array element does not contain value",
				inner.has("value") && inner.get("value").isJsonPrimitive()
			);

			assertTrue (
				"validations array element does not contain error",
				inner.has("error") && inner.get("error").isJsonPrimitive()
			);

			if (
				inner.get("field").getAsString().equals(field) &&
				inner.get("value").getAsString().equals(value) &&
				inner.get("error").getAsString().equals(error)
			) {
				match = true;
			}
		}

		assertTrue (
			"info element was not found",
			match
		);
	}

	@Then("the response has a status code of {int} and no body")
	public void verifyResponse (int code) {
		assertEquals(code, latestResults.status.value());
		assertEquals( "" , latestResults.body);
	}

	@Then("the response contains {string} objects in {string}")
	public void verifyElementList (String dataTableName, String element, DataTable data) {
		// generate a json array using a previously stored template
		JsonArray array = jsonObjectService.generateJsonArray(
			dataTableService.get(dataTableName), data);

		JsonObject obj = JsonParser.parseString(latestResults.body).getAsJsonObject();

		assertTrue ("does not have element " + element, obj.has(element));
		assertTrue (element + " is not an array", obj.get(element).isJsonArray());

		assertTrue(
			jsonObjectService.jsonArrayMatches(obj.get(element).getAsJsonArray(), array)
		);
	}

}




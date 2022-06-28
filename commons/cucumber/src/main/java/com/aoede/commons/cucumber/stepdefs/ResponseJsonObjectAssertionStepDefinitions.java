package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.service.AbstractTestService;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.HeadersService;
import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;

public class ResponseJsonObjectAssertionStepDefinitions extends BaseStepDefinition {

	public ResponseJsonObjectAssertionStepDefinitions (
		ServerProperties serverProperties,
		AbstractTestServiceDiscoveryService services,
		TestCaseIdTrackerService testCaseIdTrackerService,
		JsonService jsonService,
		DataTableService dataTableService,
		HeadersService headersService
	) {
		super (
			serverProperties,
			services,
			testCaseIdTrackerService,
			jsonService,
			dataTableService,
			headersService
		);
	}

	@Then("the response matches")
	public void verifyElement (DataTable data) {
		verifyElement(services.getLatestService(), data);
	}

	@Then("the {string} response matches")
	public void verifyElement (String domain, DataTable data) {
		verifyElement(services.getService(domain), data);
	}

	private void verifyElement (AbstractTestService service, DataTable data) {
		assertTrue(
			jsonService.jsonObjectMatches(
				service.getLatestObj(), data)
		);
	}

	@Then("the response matches {string} json")
	public void verifyJson (String json) {
		verifyJson(services.getLatestService(), json);
	}

	@Then("the {string} response matches {string} json")
	public void verifyJson (String domain, String json) {
		verifyJson(services.getService(domain), json);
	}

	private void verifyJson (AbstractTestService service, String json) {
		JsonElement data = jsonService.get(json);

		assertNotNull("object " + json + " not found", data);
		assertTrue(json + " is not a json object", data.isJsonObject());

		assertTrue(
			jsonService.jsonObjectMatches(
				service.getLatestObj(),
				data.getAsJsonObject()
			)
		);
	}

	@Then("the response contains {string} objects in {string}")
	public void verifyElementList (String dataTableName, String element, DataTable data) {
		verifyElementList (services.getLatestService(), dataTableName, element, data);
	}

	@Then("the {string} response contains {string} objects in {string}")
	public void verifyElementList (String domain, String dataTableName, String element, DataTable data) {
		verifyElementList (services.getService(domain), dataTableName, element, data);
	}

	private void verifyElementList (AbstractTestService service, String dataTableName, String element, DataTable data) {
		// generate a json array using a previously stored template
		JsonArray array = jsonService.generateJsonArray(
			dataTableService.get(dataTableName), data);

		assertNotNull("could not generate json array", array);

		JsonObject obj = service.getLatestObj();

		assertTrue ("does not have element " + element, obj.has(element));
		assertTrue (element + " is not an array", obj.get(element).isJsonArray());

		assertTrue(
			jsonService.jsonArrayMatches(obj.get(element).getAsJsonArray(), array)
		);
	}

	@Then("{string} has {string} array of size {int}")
	public void verifyArraySize (String domain, String array, int size) {
		AbstractTestService service = services.getService(domain);

		var obj = service.getLatestObj();
		assertNotNull("latest response is not an object", obj);
		assertTrue("latest object does not contain " + array, obj.has(array));

		var element = obj.get(array);
		assertTrue(array + " is not an array", element.isJsonArray());

		int actual = element.getAsJsonArray().size();

		assertEquals (size, actual);
	}

}




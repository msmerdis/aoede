package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.service.AbstractTestService;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;

public class ResponseJsonArrayAssertionStepDefinitions extends BaseStepDefinition {

	public ResponseJsonArrayAssertionStepDefinitions (
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

	@Then("the response array contains {string} objects")
	public void verifyArrayList (String dataTableName, DataTable data) {
		verifyArrayList(services.getLatestService(), dataTableName, data);
	}

	@Then("the {string} response array contains {string} objects")
	public void verifyArrayList (String domain, String dataTableName, DataTable data) {
		verifyArrayList(services.getService(domain), dataTableName, data);
	}

	private void verifyArrayList (AbstractTestService service, String dataTableName, DataTable data) {
		// generate a json array using a previously stored template
		JsonArray array = jsonService.generateJsonArray(
			dataTableService.get(dataTableName), data);

		assertNotNull("could not generate json array", array);

		assertTrue(
			jsonService.jsonArrayMatches(service.getLatestArr(), array)
		);
	}

	@Then("the response array contains {string} with {string} value {string}")
	public void verifyElementExistInList (String id, String type, String value) {
		assertTrue(
			jsonService.jsonArrayContainsObject(services.getLatestService().getLatestArr(), id, type, value)
		);
	}

	@Then("the response array does not contain {string} with {string} value {string}")
	public void verifyElementDoesNotExistInList (String id, String type, String value) {
		assertFalse(
			jsonService.jsonArrayContainsObject(services.getLatestService().getLatestArr(), id, type, value)
		);
	}

	@Then("the response array contains latest {string}")
	public void verifyLatestElementExistsInList (String domain) {
		var latestService = services.getLatestService();

		assertTrue (
			jsonService.jsonArrayContainsObjectWithElement(
				latestService.getLatestArr(),
				latestService.getKeyName(),
				latestService.getLatestKey()
			)
		);
	}

	@Then("the response array does not contain latest {string}")
	public void verifyLatestElementDoesNotExistInList (String domain) {
		var latestService = services.getLatestService();

		assertFalse (
			jsonService.jsonArrayContainsObjectWithElement(
				latestService.getLatestArr(),
				latestService.getKeyName(),
				latestService.getLatestKey()
			)
		);
	}

	@Then("{string} returned array of size {int}")
	public void verifyResultCount (String domain, int size) {
		AbstractTestService service = services.getService(domain);

		var arr = service.getLatestArr();
		assertNotNull("latest response is not an array", arr);

		int actual = arr.size();

		assertEquals (size, actual);
	}

	@Then("{string} contains latest {string} in {string}")
	public void verifyDependentDomains (String parent, String child, String element) {
		AbstractTestService pService = services.getService(parent);
		AbstractTestService cService = services.getService(child);

		JsonObject obj = pService.getLatestObj();

		assertTrue (parent + " does not have element " + element, obj.has(element));
		assertTrue (element + " is not an array", obj.get(element).isJsonArray());

		assertTrue (
			jsonService.jsonArrayContainsObjectWithElement(
				obj.get(element).getAsJsonArray(),
				cService.getKeyName(),
				cService.getLatestKey()
			)
		);
	}

}




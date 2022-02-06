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
	public void getRequest(String path) {
		latestResults = executeGet ("/test/error/" + path, defaultHeaders());
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

	@Then("the response has a status code of {int} and no body")
	public void verifyResponse (int code) {
		assertEquals(code, latestResults.status.value());
		assertEquals( "" , latestResults.body);
	}

}




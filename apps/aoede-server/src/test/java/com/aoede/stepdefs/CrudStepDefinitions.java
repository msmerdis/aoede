package com.aoede.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.ResponseResults;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.HttpService;
import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CrudStepDefinitions extends BaseStepDefinition {

	public CrudStepDefinitions (
		ServerProperties serverProperties,
		AbstractTestServiceDiscoveryService services,
		TestCaseIdTrackerService testCaseIdTrackerService,
		JsonService jsonService,
		DataTableService dataTableService,
		HttpService httpService
	) {
		super (
			serverProperties,
			services,
			testCaseIdTrackerService,
			jsonService,
			dataTableService,
			httpService
		);
	}

	private ResponseResults latestResults = new ResponseResults ();

	/**
	 * Requests
	 */

	@When("request {string} from aoede")
	public void getRequest(String path) {
		latestResults = executeGet (path, defaultHeaders());
	}

	@When("request {string} from aoede as {string}")
	public void getRequest(String path, String json) {
		getRequest (path);

		jsonService.put(json, JsonParser.parseString(latestResults.body));
	}

	@When("request {string} from aoede as plain text")
	public void getPlainTextRequest(String path) {
		HttpHeaders headers = defaultHeaders ();
		List<MediaType> accept = new LinkedList<MediaType> ();

		accept.add(MediaType.TEXT_PLAIN);
		headers.setAccept(accept);

		latestResults = executeGet (path, headers);
	}

	/**
	 * assertions
	 */

	@Then("the aoede response has a status code of {int}")
	public void verifyStatus (int code) {
		assertEquals(code, latestResults.status.value());
	}

	@Then("the aoede response matches")
	public void verifyElement (DataTable data) {
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

	@Then("the aoede response contains {string}")
	public void verifyElement (String keyword) {
		assertNotNull("response does not have a body", latestResults.body);
		assertTrue(keyword + " is not in " + latestResults.body, latestResults.body.indexOf(keyword) >= 0);
	}

}




package com.aoede.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.aoede.commons.base.BaseStepDefinition;
import com.aoede.commons.base.ResponseResults;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CrudStepDefinitions extends BaseStepDefinition {
	private ResponseResults latestResults = new ResponseResults ();

	/**
	 * Requests
	 */

	@When("request {string} from aoede")
	public void getRequest(String path) {
		latestResults = executeGet (path, defaultHeaders());
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

}




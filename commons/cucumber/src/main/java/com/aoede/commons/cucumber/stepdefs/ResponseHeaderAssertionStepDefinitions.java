package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertTrue;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.service.AbstractTestService;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;

import io.cucumber.java.en.Then;

public class ResponseHeaderAssertionStepDefinitions extends BaseStepDefinition {

	public ResponseHeaderAssertionStepDefinitions (
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

	@Then("the response returned header {string}")
	public void verifyHeader (String header) {
		verifyHeader (services.getLatestService(), header);
	}

	@Then("the {string} response returned header {string}")
	public void verifyHeader (String domain, String header) {
		verifyHeader (services.getService(domain), header);
	}

	private void verifyHeader (AbstractTestService service, String header) {
		assertTrue(
			"header " + header + " not found in response",
			service.getLatestResults().headers.containsKey(header)
		);
	}

	@Then("the response header {string} contains {string}")
	public void matchHeader (String header, String value) {
		matchHeader (services.getLatestService(), header, value);
	}

	@Then("the {string} response header {string} contains {string}")
	public void matchHeader (String domain, String header, String value) {
		matchHeader (services.getService(domain), header, value);
	}

	private void matchHeader (AbstractTestService service, String header, String value) {
		verifyHeader(service, header);

		assertTrue(
			"header " + header + " not found in response",
			service.getLatestResults().headers.get(header).contains(value)
		);
	}

}




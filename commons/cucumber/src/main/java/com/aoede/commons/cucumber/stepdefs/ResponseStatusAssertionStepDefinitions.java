package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.service.AbstractTestService;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.HttpService;
import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;

import io.cucumber.java.en.Then;

public class ResponseStatusAssertionStepDefinitions extends BaseStepDefinition {

	public ResponseStatusAssertionStepDefinitions (
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

	@Then("the request was successful")
	public void verifySuccessfulRequest () {
		verifySuccessfulRequest(services.getLatestService());
	}

	@Then("the {string} request was successful")
	public void verifySuccessfulRequest (String domain) {
		verifySuccessfulRequest(services.getService(domain));
	}

	private void verifySuccessfulRequest(AbstractTestService service) {
		assertTrue("request was not successful", service.isSuccess());
	}

	@Then("the request was not successful")
	public void verifyNotSuccessfulRequest () {
		verifyNotSuccessfulRequest(services.getLatestService());
	}

	@Then("the {string} request was not successful")
	public void verifyNotSuccessfulRequest (String domain) {
		verifyNotSuccessfulRequest(services.getService(domain));
	}

	private void verifyNotSuccessfulRequest(AbstractTestService service) {
		assertFalse("request was successful", service.isSuccess());
	}

	@Then("the response has a status code of {int}")
	public void verifyStatus (int code) {
		verifyStatus(services.getLatestService(), code);
	}

	@Then("the {string} response has a status code of {int}")
	public void verifyStatus (String domain, int code) {
		verifyStatus(services.getService(domain), code);
	}

	private void verifyStatus (AbstractTestService service, int code) {
		assertEquals(code, service.getLatestResults().status.value());
	}

}




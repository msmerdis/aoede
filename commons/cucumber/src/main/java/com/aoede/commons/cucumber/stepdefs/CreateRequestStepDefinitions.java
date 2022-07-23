package com.aoede.commons.cucumber.stepdefs;

import java.util.Collections;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.ResponseResults;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.HttpService;
import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;

public class CreateRequestStepDefinitions extends BaseStepDefinition {

	public CreateRequestStepDefinitions (
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

	@Given("a {string} with")
	public void createWithTable(String domain, DataTable data) {
		create (domain, jsonService.generateJsonObject(data).toString());
	}

	@Given("a {string} with {string} json element")
	public void createWithElement(String domain, String element) {
		create (domain, jsonService.get(element).toString());
	}

	@Given("a {string} with {string} body")
	public void createWithBody(String domain, String body) {
		create (domain, body);
	}

	@Given("a {string} without data")
	public void createEmpty(String domain) {
		createWithTable (domain, buildDataTable(Collections.emptyList()));
	}

	private void create(String domain, String data) {
		logger.info("creating " + domain);
		ResponseResults results = executePost (
			services.getPathForService(domain),
			data
		);

		// update test controller with data
		services.getLatestService(domain).createResults(results);
	}

}




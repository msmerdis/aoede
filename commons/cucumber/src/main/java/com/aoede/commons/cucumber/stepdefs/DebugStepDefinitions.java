package com.aoede.commons.cucumber.stepdefs;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.HttpService;
import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;

public class DebugStepDefinitions extends BaseStepDefinition {

	public DebugStepDefinitions (
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

	@When("log json")
	public void logJson(DataTable data) {
		logger.error(jsonService.generateJsonObject(data).toString());
	}

	@When("log {string} json")
	public void logJson(String name) {
		logger.error(jsonService.get(name).toString());
	}

	@When("log {string} response")
	public void logResponse(String domain) {
		logger.error(services.getService(domain).getLatestResults().headers.toString());
		logger.error(services.getService(domain).getLatestResults().status.toString());
		logger.error(services.getService(domain).getLatestResults().body);
	}

}




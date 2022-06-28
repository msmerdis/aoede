package com.aoede.commons.cucumber.stepdefs;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.HeadersService;
import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;

public class DataPreparationStepDefinitions extends BaseStepDefinition {

	public DataPreparationStepDefinitions (
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

	@When("prepare composite id {string}")
	public void prepareCompositeId(String name, DataTable data) {
		jsonService.putCompositeKey(name, data);
	}

	@When("prepare json {string} with {string} as {string}")
	public void prepareJsonObject(String name, String value, String type) {
		jsonService.put(name,
			jsonService.generateJsonElement(type, value));
	}

	@When("prepare json {string}")
	public void prepareJsonObject(String name, DataTable data) {
		jsonService.putObject(name, data);
	}

	@When("prepare empty json object {string}")
	public void prepareEmptyJsonObject(String name) {
		jsonService.put(name, new JsonObject());
	}

	@When("prepare json array {string}")
	public void prepareJsonArray(String name, DataTable data) {
		jsonService.putArray(name, data);
	}

	@When("prepare empty json array {string}")
	public void prepareJsonArray(String name) {
		jsonService.put(name, new JsonArray());
	}

	@When("prepare json array {string} of {string}")
	public void prepareJsonArray(String name, String template, DataTable data) {
		jsonService.putArray(name, dataTableService.get(template), data);
	}

	@When("prepare data table {string}")
	public void prepareDataTable(String name, DataTable data) {
		dataTableService.put(name, data);
	}

	@When("prepare url {string}")
	public void prepareUrl(String url) {
		globalUrl = url;
	}

	@When("prepare url")
	public void prepareUrl(DataTable data) {
		globalUrl = jsonService.generateUrl(data);
	}

	@When("set header {string} to {string} as {string}")
	public void setHeader(String name, String value, String type) {
		headersService.set(name, jsonService.generateJsonElement(type, value).toString());
	}

	@When("add header {string} to {string} as {string}")
	public void addHeader(String name, String value, String type) {
		headersService.add(name, jsonService.generateJsonElement(type, value).toString());
	}
}




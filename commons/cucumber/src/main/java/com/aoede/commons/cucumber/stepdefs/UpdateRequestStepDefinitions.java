package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertNotNull;

import java.util.Collections;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.ResponseResults;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.HeadersService;
import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;
import com.google.gson.JsonElement;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;

public class UpdateRequestStepDefinitions extends BaseStepDefinition {

	public UpdateRequestStepDefinitions (
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

	@When("update {string} with id {string}")
	public void updateSimpleWithIdAndTable(String domain, String id, DataTable data) {
		updateSimple(domain, id, jsonService.generateJsonObject(data).toString());
	}

	@When("update {string} with id {string} and {string} json element")
	public void updateSimpleWithIdAndElement(String domain, String id, String element) {
		updateSimple(domain, id, jsonService.get(element).toString());
	}

	@When("update {string} with id {string} and {string} body")
	public void updateSimpleWithIdAndBody(String domain, String id, String body) {
		updateSimple(domain, id, body);
	}

	@When("update {string} with id {string} without data")
	public void updateSimpleWithIdEmpty(String domain, String id) {
		updateSimpleWithIdAndTable(domain, id, buildDataTable(Collections.emptyList()));
	}

	private String getCompositeId (String name) {
		String id = jsonService.getCompositeKey(name);

		assertNotNull(id, "composite id has not been prepared");

		return id;
	}

	@When("update {string} with composite id {string}")
	public void updateWithCompositeIdAndTable(String domain, String name, DataTable data) {
		updateSimpleWithIdAndTable(domain, getCompositeId(name), data);
	}

	@When("update {string} with composite id {string} and {string} json element")
	public void updateWithCompositeIdAndElement(String domain, String name, String element) {
		updateSimpleWithIdAndElement(domain, getCompositeId(name), element);
	}

	@When("update {string} with composite id {string} and {string} body")
	public void updateWithCompositeIdAndBody(String domain, String name, String body) {
		updateSimpleWithIdAndBody(domain, getCompositeId(name), body);
	}

	@When("update {string} with composite id {string} without data")
	public void updateWithCompositeIdEmpty(String domain, String name) {
		updateSimpleWithIdEmpty(domain, getCompositeId(name));
	}

	private String getPreviouslyCreatedId (String domain) {
		assertNotNull("Service for " + domain + " not found", services.getLatestService(domain));

		JsonElement element = services.getService(domain).getLatestKey();

		assertNotNull(domain + " has not been created", element);

		return element.getAsString();
	}

	@When("update previously created {string}")
	public void updateWithLatestAndTable(String domain, DataTable data) {
		updateSimpleWithIdAndTable(domain, getPreviouslyCreatedId(domain), data);
	}

	@When("update previously created {string} with {string} json element")
	public void updateWithLatestAndElement(String domain, String element) {
		updateSimpleWithIdAndElement(domain, getPreviouslyCreatedId(domain), element);
	}

	@When("update previously created {string} with {string} body")
	public void updateWithLatestAndBody(String domain, String body) {
		updateSimpleWithIdAndBody(domain, getPreviouslyCreatedId(domain), body);
	}

	@When("update previously created {string} without data")
	public void updateWithLatestEmpty(String domain) {
		updateSimpleWithIdEmpty(domain, getPreviouslyCreatedId(domain));
	}

	private void updateSimple(String domain, String id, String data) {
		logger.info("updating " + domain + " with id " + id);
		ResponseResults results = executePut (
			services.getPathForService(domain, "/" + id),
			data
		);

		// update test controller with data
		services.getLatestService(domain).updateResults(results);
	}

}




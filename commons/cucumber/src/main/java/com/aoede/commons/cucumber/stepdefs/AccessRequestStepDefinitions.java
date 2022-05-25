package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertNotNull;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpHeaders;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.ResponseResults;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;
import com.google.gson.JsonElement;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;

public class AccessRequestStepDefinitions extends BaseStepDefinition {

	public AccessRequestStepDefinitions (
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

	///////////////////////////////////////////////////////////////////////////////////////////////
	// SEARCH

	@When("search {string} with keyword {string}")
	public void search(String domain, String keyword) {
		logger.info("searching " + domain + " for " + keyword);
		HttpHeaders headers = defaultHeaders();

		headers.add("X-Search-Terms", keyword);

		ResponseResults results = executeGet (services.getPathForService(domain, "/search"), headers);

		// update test controller with data
		services.getLatestService(domain).searchResults(results);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// ACCESS SINGLE ITEM

	@When("request a {string} with id {string}")
	public void getSimple(String domain, String id) {
		logger.info("retrieve " + domain + " with id " + id);

		ResponseResults results = executeGet (services.getPathForService(domain, "/" + id));

		// update test controller with data
		services.getLatestService(domain).accessResults(results);
	}

	@When("request a {string} with composite id")
	public void getComposite(String domain, DataTable data) {
		getSimple(domain, jsonService.generateCompositeKey(data));
	}

	@When("request a {string} with composite id {string}")
	public void getCompositePrepared(String domain, String name) {
		String id = jsonService.getCompositeKey(name);

		assertNotNull("composite id has not been prepared", id);

		getSimple(domain, id);
	}

	@When("request previously created {string}")
	public void getLatest (String domain) {
		JsonElement element = services.getService(domain).getLatestKey();

		assertNotNull(domain + " has not been created", element);

		getSimple (domain, element.getAsString());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// ACCESS MULTIPLE ITEMS

	@When("request all available {string}")
	public void findAll (String domain) {
		logger.info("retrieving all available " + domain);
		ResponseResults results = executeGet (services.getPathForService(domain));

		// update test controller with data
		services.getLatestService(domain).findAllResults(results);
	}

	@When("request all available {string} for latest {string}")
	public void findAllForLatest (String childDomain, String parentDomain) {
		logger.info("retrieving all available " + childDomain + " under " + parentDomain);

		ResponseResults results = executeGet (
			services.getPathForService(childDomain)
				+ "/" + services.getService(parentDomain).getName()
				+ "/" + services.getService(parentDomain).getLatestKey()
		);

		// update test controller with data
		services.getService(childDomain).findAllResults(results);
	}

}




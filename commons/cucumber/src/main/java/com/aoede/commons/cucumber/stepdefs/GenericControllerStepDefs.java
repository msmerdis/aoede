package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpHeaders;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.ResponseResults;
import com.aoede.commons.cucumber.service.AbstractTestService;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.CompositeIdService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.JsonObjectService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * GenericTestController
 *
 * Provides the base step definitions for a domain controller request and assertion
 * Utilises domain test services to build and verify the results
 */
public class GenericControllerStepDefs extends BaseStepDefinition {

	public GenericControllerStepDefs (
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

	/**
	 * Data preparation steps
	 */

	@When("prepare composite id {string}")
	public void prepareCompositeId(String name, DataTable data) {
		compositeIdService.put(name, data);
	}

	@When("prepare json {string}")
	public void prepareJson(String name, DataTable data) {
		jsonObjectService.put(name, data);
	}

	@When("prepare data table {string}")
	public void prepareDataTable(String name, DataTable data) {
		dataTableService.put(name, data);
	}

	/**
	 * Requests
	 */

	@When("search {string} with keyword {string}")
	public void search(String domain, String keyword) {
		logger.info("searching " + domain + " for " + keyword);
		HttpHeaders headers = defaultHeaders();

		headers.add("X-Search-Terms", keyword);

		ResponseResults results = executeGet (services.getPathForService(domain, "/search"), headers);

		// update test controller with data
		services.getLatestService(domain).searchResults(results);
	}

	@When("request a {string} with id {string}")
	public void getSimple(String domain, String id) {
		logger.info("retrieve " + domain + " with id " + id);

		ResponseResults results = executeGet (services.getPathForService(domain, "/" + id));

		// update test controller with data
		services.getLatestService(domain).accessResults(results);
	}

	@When("request a {string} with composite id")
	public void getComposite(String domain, DataTable data) {
		getSimple(domain, compositeIdService.generateCompositeKey(data));
	}

	@When("request a {string} with composite id {string}")
	public void getCompositePrepared(String domain, String name) {
		String id = compositeIdService.get(name);

		assertNotNull("composite id has not been prepared", id);

		getSimple(domain, id);
	}

	@When("request previously created {string}")
	public void getLatest (String domain) {
		JsonElement element = services.getService(domain).getLatestKey();

		assertNotNull(domain + " has not been created", element);

		getSimple (domain, element.getAsString());
	}

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

	@Given("a {string} with")
	public void create(String domain, DataTable data) {
		logger.info("creating " + domain);
		ResponseResults results = executePost (
			services.getPathForService(domain),
			jsonObjectService.generateJson(data).toString()
		);

		// update test controller with data
		services.getLatestService(domain).createResults(results);
	}

	@When("update {string} with id {string}")
	public void updateSimple(String domain, String id, DataTable data) {
		logger.info("updating " + domain + " with id " + id);
		ResponseResults results = executePut (
			services.getPathForService(domain, "/" + id),
			jsonObjectService.generateJson(data).toString()
		);

		// update test controller with data
		services.getLatestService(domain).updateResults(results);
	}

	@When("update {string} with composite id {string}")
	public void updateCompositePrepared(String domain, String name, DataTable data) {
		String id = compositeIdService.get(name);

		assertNotNull(id, "composite id has not been prepared");

		updateSimple(domain, id, data);
	}

	@When("update previously created {string}")
	public void updateLatest (String domain, DataTable data) {
		JsonElement element = services.getService(domain).getLatestKey();

		assertNotNull(domain + " has not been created", element);

		updateSimple (domain, element.getAsString(), data);
	}

	@When("delete {string} with id {string}")
	public void deleteSimple(String domain, String id) {
		logger.info("deleting " + domain + " with id " + id);
		ResponseResults results = executeDelete (services.getPathForService(domain, "/" + id));

		// update test controller with data
		services.getLatestService(domain).deleteResults(results);
	}

	@When("delete {string} with composite id {string}")
	public void deleteCompositePrepared(String domain, String name) {
		String id = compositeIdService.get(name);

		assertNotNull(id, "composite id has not been prepared");

		deleteSimple(domain, id);
	}

	@When("delete {string} with composite id")
	public void deleteComposite(String domain, DataTable data) {
		deleteSimple(domain, compositeIdService.generateCompositeKey(data));
	}

	@When("delete previously created {string}")
	public void deleteLatest (String domain) {
		JsonElement element = services.getService(domain).getLatestKey();

		assertNotNull(domain + " has not been created", element);

		deleteSimple (domain, element.getAsString());
	}

	/**
	 * assertions
	 */

	@Then("the request was successful")
	public void verifySuccessfulRequest () {
		assertTrue("request was not successful", services.getLatestService().isSuccess());
	}

	@Then("the request was not successful")
	public void verifyNotSuccessfulRequest () {
		assertFalse("request was successful", services.getLatestService().isSuccess());
	}

	@Then("the response has a status code of {int}")
	public void verifyStatus (int code) {
		assertEquals(code, services.getLatestService().getLatestResults().status.value());
	}

	@Then("the response array contains {string} objects")
	public void verifyElementList (String dataTableName, DataTable data) {
		// generate a json array using a previously stored template
		JsonArray array = jsonObjectService.generateJsonArray(
			dataTableService.get(dataTableName), data);

		assertTrue(
			jsonObjectService.jsonArrayMatches(services.getLatestService().getLatestArr(), array)
		);
	}

	@Then("the response array contains {string} objects in {string}")
	public void verifyElementList (String dataTableName, String element, DataTable data) {
		// generate a json array using a previously stored template
		JsonArray array = jsonObjectService.generateJsonArray(
			dataTableService.get(dataTableName), data);

		JsonObject obj = services.getLatestService().getLatestObj();

		assertTrue ("does not have element " + element, obj.has(element));
		assertTrue (element + " is not an array", obj.get(element).isJsonArray());

		assertTrue(
			jsonObjectService.jsonArrayMatches(obj.get(element).getAsJsonArray(), array)
		);
	}

	@Then("the response array contains {string} with {string} value {string}")
	public void verifyElementExistInList (String id, String type, String value) {
		assertTrue(
			jsonObjectService.jsonArrayContainsObject(services.getLatestService().getLatestArr(), id, type, value)
		);
	}

	@Then("the response array does not contain {string} with {string} value {string}")
	public void verifyElementDoesNotExistInList (String id, String type, String value) {
		assertFalse(
			jsonObjectService.jsonArrayContainsObject(services.getLatestService().getLatestArr(), id, type, value)
		);
	}

	@Then("the response array contains latest {string}")
	public void verifyLatestElementExistsInList (String domain) {
		var latestService = services.getLatestService();

		assertTrue (
			jsonObjectService.jsonArrayContainsObjectWithElement(
				latestService.getLatestArr(),
				latestService.getKeyName(),
				latestService.getLatestKey()
			)
		);
	}

	@Then("the response array does not contain latest {string}")
	public void verifyLatestElementDoesNotExistInList (String domain) {
		var latestService = services.getLatestService();

		assertFalse (
			jsonObjectService.jsonArrayContainsObjectWithElement(
				latestService.getLatestArr(),
				latestService.getKeyName(),
				latestService.getLatestKey()
			)
		);
	}

	@Then("{string} contains latest {string} in {string}")
	public void verifyDependentDomains (String parent, String child, String element) {
		AbstractTestService pService = services.getService(parent);
		AbstractTestService cService = services.getService(child);

		JsonObject obj = pService.getLatestObj();

		assertTrue (parent + " does not have element " + element, obj.has(element));
		assertTrue (element + " is not an array", obj.get(element).isJsonArray());

		assertTrue (
			jsonObjectService.jsonArrayContainsObjectWithElement(
				obj.get(element).getAsJsonArray(),
				cService.getKeyName(),
				cService.getLatestKey()
			)
		);
	}

	@Then("{string} returned array of size {int}")
	public void verifyResultCount (String domain, int size) {
		AbstractTestService service = services.getService(domain);

		var arr = service.getLatestArr();
		assertNotNull("latest response is not an array", arr);

		int actual = arr.size();

		assertEquals (size, actual);
	}

	@Then("the response matches")
	public void verifyElement (DataTable data) {
		assertTrue(
			jsonObjectService.jsonObjectMatches(
				services.getLatestService().getLatestObj(), data)
		);
	}

	@Then("{string} has {string} array of size {int}")
	public void verifyArraySize (String domain, String array, int size) {
		AbstractTestService service = services.getService(domain);

		var obj = service.getLatestObj();
		assertNotNull("latest response is not an object", obj);
		assertTrue("latest object does not contain " + array, obj.has(array));

		var element = obj.get(array);
		assertTrue(array + " is not an array", element.isJsonArray());

		int actual = element.getAsJsonArray().size();

		assertEquals (size, actual);
	}
}




package com.aoede.commons.base.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.springframework.http.HttpHeaders;

import com.aoede.commons.base.BaseStepDefinition;
import com.aoede.commons.base.ResponseResults;
import com.aoede.commons.service.AbstractTestService;
import com.google.gson.JsonElement;

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
		services.getService(domain).searchResults(results);
	}

	@When("request a {string} with id {string}")
	public void getSimple(String domain, String id) {
		logger.info("retrieve " + domain + " with id " + id);

		ResponseResults results = executeGet (services.getPathForService(domain, "/" + id));

		// update test controller with data
		services.getService(domain).accessResults(results);
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
		services.getService(domain).findAllResults(results);
	}

	@When("request all available {string} for latest {string}")
	public void findAll (String childDomain, String parentDomain) {
		logger.info("retrieving all available " + childDomain + " under "
			+ services.getPathForService(parentDomain)
			+ "(" + services.getService(parentDomain).getLatestKey() + ")");

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
		services.getService(domain).createResults(results);
	}

	@When("update {string} with id {string}")
	public void updateSimple(String domain, String id, DataTable data) {
		logger.info("updating " + domain + " with id " + id);
		ResponseResults results = executePut (
			services.getPathForService(domain, "/" + id),
			jsonObjectService.generateJson(data).toString()
		);

		// update test controller with data
		services.getService(domain).updateResults(results);
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
		services.getService(domain).deleteResults(results);
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
		AbstractTestService service = services.getService();
		assertTrue(service.getLatestResults().body, services.getService().isSuccess());
	}

	@Then("the request was not successful")
	public void verifyNotSuccessfulRequest () {
		AbstractTestService service = services.getService();
		assertFalse(service.getLatestResults().body, services.getService().isSuccess());
	}

	@Then("the response has a status code of {int}")
	public void verifyStatus (int code) {
		AbstractTestService service = services.getService();
		assertEquals(code, service.getLatestResults().status.value());
	}

	@Then("the response array contains")
	public void verifyElementList (DataTable data) {
		assertTrue(
			services.getService().getLatestResults().body,
			services.getService().lastArrayMatches(data)
		);
	}

	@Then("the response array contains {string} with value {string}")
	public void verifyElementExistInList (String id, String value) {
		assertTrue(
			services.getService().getLatestResults().body,
			services.getService().lastArrayContainsObjectWith(id, value)
		);
	}

	@Then("the response array contains latest {string}")
	public void verifyLatestElementExistsInList (String domain) {
		assertTrue (
			services.getService().getLatestResults().body,
			services.getService().lastArrayContainsObjectWith(
				services.getService(domain).getKeyName(),
				services.getService(domain).getLatestKey().getAsString()
			)
		);
	}

	@Then("the response array does not contain latest {string}")
	public void verifyLatestElementDoesNotExistInList (String domain) {
		assertFalse (
			services.getService().getLatestResults().body,
			services.getService().lastArrayContainsObjectWith(
				services.getService(domain).getKeyName(),
				services.getService(domain).getLatestKey().getAsString()
			)
		);
	}

	@Then("the response array does not contain {string} with value {string}")
	public void verifyElementDoesNotExistInList (String id, String value) {
		assertFalse(
			services.getService().getLatestResults().body,
			services.getService().lastArrayContainsObjectWith(id, value)
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
			services.getService().getLatestResults().body,
			services.getService().lastObjectMatches(data)
		);
	}

	@Then("{string} contains latest {string} in {string}")
	public void verifyDependentDomains (String parent, String child, String element) {
		AbstractTestService pService = services.getService(parent);
		AbstractTestService cService = services.getService(child);

		assertTrue (pService.getLatestResults().body, pService.containsKeyInElement(
			element,
			cService.getKeyName(),
			cService.getLatestKey().getAsString()
		));
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




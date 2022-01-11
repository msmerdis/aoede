package com.aoede.commons.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.http.HttpHeaders;

import com.aoede.commons.base.ResponseResults;
import com.aoede.commons.base.ServiceStepDefinition;
import com.aoede.commons.service.AbstractTestService;

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
public class GenericTestController extends ServiceStepDefinition {
	private static final long serialVersionUID = 1L;

	/**
	 * Data preparation steps
	 */

	public String generateCompositeKey (DataTable data) {
		return generatePaddedBase64(generateJson(data));
	}

	@When("prepare composite id {string}")
	public void search(String name, DataTable data) {
		put(name, generateCompositeKey(data));
	}

	/**
	 * Requests
	 */

	@When("search {string} with keyword {string}")
	public void search(String domain, String keyword) {
		logger.info("searching " + domain + " for " + keyword);
		HttpHeaders headers = defaultHeaders();

		headers.add("X-Search-Terms", keyword);

		ResponseResults results = executeGet (getPath(domain, "/search"), headers);

		// update test controller with data
		getService(domain).searchResults(results);
	}

	@When("request a {string} with id {string}")
	public void getSimple(String domain, String id) {
		logger.info("retrieve " + domain + " with id " + id);

		ResponseResults results = executeGet (getPath(domain, "/" + id));

		// update test controller with data
		getService(domain).accessResults(results);
	}

	@When("request a {string} with composite id")
	public void getComposite(String domain, DataTable data) {
		getSimple(domain, generateCompositeKey (data));
	}

	@When("request a {string} with composite id {string}")
	public void getCompositePrepared(String domain, String name) {
		String id = get(name);

		assertNotNull(id, "composite id has not been prepared");

		getSimple(domain, id);
	}

	@When("request previously created {string}")
	public void getLatest (String domain) {
		getSimple (domain, getService(domain).getLatestKey());
	}

	@When("request all available {string}")
	public void findAll (String domain) {
		logger.info("retrieving all available " + domain);
		ResponseResults results = executeGet (getPath(domain));

		// update test controller with data
		getService(domain).findAllResults(results);
	}

	@When("request all available {string} for latest {string}")
	public void findAll (String childDomain, String parentDomain) {
		logger.info("retrieving all available " + childDomain + " under " + getPath(parentDomain) + "(" + getService(parentDomain).getLatestKey() + ")");
		ResponseResults results = executeGet (getPath(childDomain) + "/" + getService(parentDomain).getName() + "/" + getService(parentDomain).getLatestKey());

		// update test controller with data
		getService(childDomain).findAllResults(results);
	}

	@Given("a randomized {string}")
	public void create(String domain) {
		logger.info("creating " + domain);
		ResponseResults results = executePost (getPath(domain), getService(domain).createBody());

		// update test controller with data
		getService(domain).createResults(results);
	}

	@Given("a {string} with")
	public void create(String domain, DataTable data) {
		logger.info("creating " + domain);
		ResponseResults results = executePost (getPath(domain), getService(domain).createBody(data));

		// update test controller with data
		getService(domain).createResults(results);
	}

	@When("update {string} with id {string}")
	public void updateSimple(String domain, String id, DataTable data) {
		logger.info("updating " + domain + " with id " + id);
		ResponseResults results = executePut (getPath(domain, "/" + id), getService(domain).createBody(data));

		// update test controller with data
		getService(domain).updateResults(results);
	}

	@When("update {string} with composite id {string}")
	public void updateCompositePrepared(String domain, String name, DataTable data) {
		String id = get(name);

		assertNotNull(id, "composite id has not been prepared");

		updateSimple(domain, id, data);
	}

	@When("update previously created {string}")
	public void updateLatest (String domain, DataTable data) {
		updateSimple (domain, getService(domain).getLatestKey(), data);
	}

	@When("delete {string} with id {string}")
	public void deleteSimple(String domain, String id) {
		logger.info("deleting " + domain + " with id " + id);
		ResponseResults results = executeDelete (getPath(domain, "/" + id));

		// update test controller with data
		getService(domain).deleteResults(results);
	}

	@When("delete {string} with composite id {string}")
	public void deleteCompositePrepared(String domain, String name) {
		String id = get(name);

		assertNotNull(id, "composite id has not been prepared");

		deleteSimple(domain, id);
	}

	@When("delete {string} with composite id")
	public void deleteComposite(String domain, DataTable data) {
		deleteSimple(domain, generateCompositeKey (data));
	}

	@When("delete previously created {string}")
	public void deleteLatest (String domain) {
		deleteSimple (domain, getService(domain).getLatestKey());
	}

	/**
	 * assertions
	 */

	@Then("the request was successful")
	public void verifySuccessfulRequest () {
		AbstractTestService service = getService();
		assertTrue(service.getLatestResults().body, getService().isSuccess());
	}

	@Then("the request was not successful")
	public void verifyNotSuccessfulRequest () {
		AbstractTestService service = getService();
		assertFalse(service.getLatestResults().body, getService().isSuccess());
	}

	@Then("the response has a status code of {int}")
	public void verifyStatus (int code) {
		AbstractTestService service = getService();
		assertEquals(code, service.getLatestResults().status.value());
	}

	@Then("the response array contains")
	public void verifyElementList (DataTable data) {
		assertTrue(
			getService().getLatestResults().body,
			getService().lastArrayMatches(data)
		);
	}

	@Then("the response array contains {string} with value {string}")
	public void verifyElementExistInList (String id, String value) {
		assertTrue(
			getService().getLatestResults().body,
			getService().lastArrayContainsObjectWith(id, value)
		);
	}

	@Then("the response array contains latest {string}")
	public void verifyLatestElementExistsInList (String domain) {
		assertTrue (
			getService().getLatestResults().body,
			getService().lastArrayContainsObjectWith(
				getService(domain).getKeyName(),
				getService(domain).getLatestKey()
			)
		);
	}

	@Then("the response array does not contain latest {string}")
	public void verifyLatestElementDoesNotExistInList (String domain) {
		assertFalse (
			getService().getLatestResults().body,
			getService().lastArrayContainsObjectWith(
				getService(domain).getKeyName(),
				getService(domain).getLatestKey()
			)
		);
	}

	@Then("the response array does not contain {string} with value {string}")
	public void verifyElementDoesNotExistInList (String id, String value) {
		assertFalse(
			getService().getLatestResults().body,
			getService().lastArrayContainsObjectWith(id, value)
		);
	}

	@Then("{string} returned array of size {int}")
	public void verifyResultCount (String domain, int size) {
		AbstractTestService service = getService(domain);

		var arr = service.getLatestArr();
		assertNotNull(arr, "latest response is not an array");

		int actual = arr.size();

		assertEquals (size, actual);
	}

	@Then("the response matches")
	public void verifyElement (DataTable data) {
		assertTrue(
			getService().getLatestResults().body,
			getService().lastObjectMatches(data)
		);
	}

	@Then("{string} contains latest {string} in {string}")
	public void verifyDependentDomains (String parent, String child, String element) {
		AbstractTestService pService = getService(parent);
		AbstractTestService cService = getService(child);

		assertTrue (pService.getLatestResults().body, pService.containsKeyInElement(
			element,
			cService.getKeyName(),
			cService.getLatestKey()
		));
	}

	@Then("{string} has {string} array of size {int}")
	public void verifyArraySize (String domain, String array, int size) {
		AbstractTestService service = getService(domain);

		var obj = service.getLatestObj();
		assertNotNull(obj, "latest response is not an object");
		assertTrue("latest object does not contain " + array, obj.has(array));

		var element = obj.get(array);
		assertTrue(array + " is not an array", element.isJsonArray());

		int actual = element.getAsJsonArray().size();

		assertEquals (size, actual);
	}
}




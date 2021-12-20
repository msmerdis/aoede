package com.aoede.commons.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.springframework.http.HttpHeaders;

import com.aoede.commons.base.ServiceStepDefinition;

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

	/**
	 * Requests
	 */

	@When("search {string} with keyword {string}")
	public void search(String domain, String keyword) {
		logger.info("searching " + domain + " for " + keyword);
		HttpHeaders headers = defaultHeaders();

		headers.add("X-Search-Terms", keyword);

		executeGet (getPath(domain, "/search"), headers);

		// update test controller with data
		getService(domain).searchResults(
			getResponseStatus(),
			getResponseHeaders(),
			getResponseBody()
		);
	}

	@When("request a {string} with id {string}")
	public void get(String domain, String id) {
		logger.info("retrieve " + domain + " with id " + id);

		executeGet (getPath(domain, "/" + id));

		// update test controller with data
		getService(domain).accessResults(
			getResponseStatus(),
			getResponseHeaders(),
			getResponseBody()
		);
	}

	@When("request previously created {string}")
	public void getLatest (String domain) {
		get (domain, getService(domain).getLatestKey());
	}

	@When("request all available {string}")
	public void findAll (String domain) {
		logger.info("retrieving all available " + domain);

		executeGet (getPath(domain));

		// update test controller with data
		getService(domain).findAllResults(
			getResponseStatus(),
			getResponseHeaders(),
			getResponseBody()
		);
	}

	@Given("a randomized {string}")
	public void create(String domain) {
		logger.info("creating " + domain);

		executePost (getPath(domain), getService(domain).createBody());

		// update test controller with data
		getService(domain).createResults(
			getResponseStatus(),
			getResponseHeaders(),
			getResponseBody()
		);
	}

	@Given("a {string} with")
	public void create(String domain, DataTable data) {
		logger.info("creating " + domain);

		executePost (getPath(domain), getService(domain).createBody(data));

		// update test controller with data
		getService(domain).createResults(
			getResponseStatus(),
			getResponseHeaders(),
			getResponseBody()
		);
	}

	@When("update {string} with id {string}")
	public void update(String domain, String id, DataTable data) {
		logger.info("updating " + domain + " with id " + id);

		executePut (getPath(domain, "/" + id), getService(domain).createBody(data));

		// update test controller with data
		getService(domain).updateResults(
			getResponseStatus(),
			getResponseHeaders(),
			getResponseBody()
		);
	}

	@When("update previously created {string}")
	public void updateLatest (String domain, DataTable data) {
		update (domain, getService(domain).getLatestKey(), data);
	}

	@When("delete {string} with id {string}")
	public void delete(String domain, String id) {
		logger.info("deleting " + domain + " with id " + id);

		executeDelete (getPath(domain, "/" + id));

		// update test controller with data
		getService(domain).deleteResults(
			getResponseStatus(),
			getResponseHeaders(),
			getResponseBody()
		);
	}

	@When("delete previously created {string}")
	public void deleteLatest (String domain) {
		delete (domain, getService(domain).getLatestKey());
	}

	/**
	 * assertions
	 */

	@Then("the request was successful")
	public void verifySuccessfulRequest () {
		assertTrue(getResponseBody(), getService().isSuccessful());
	}

	@Then("the request was not successful")
	public void verifyNotSuccessfulRequest () {
		assertFalse(getResponseBody(), getService().isSuccessful());
	}

	@Then("the response has a status code of {int}")
	public void verifyStatus (int code) {
		assertEquals(code, getResponseStatus().value());
	}

	@Then("the response array contains")
	public void verifyElementList (DataTable data) {
		assertTrue(getService().lastArrayMatches(data));
	}

	@Then("the response array contains {string} with value {string}")
	public void verifyElementExistInList (String id, String value) {
		assertTrue(getService().lastArrayContainsObjectWith(id, value));
	}

	@Then("the response array contains latest {string}")
	public void verifyLatestElementExistsInList (String domain) {
		assertTrue (
			getService(domain).lastArrayContainsObjectWith(
				getService(domain).getKeyName(),
				getService(domain).getLatestKey()
			)
		);
	}

	@Then("the response array does not contain latest {string}")
	public void verifyLatestElementDoesNotExistInList (String domain) {
		assertFalse (
			getService(domain).lastArrayContainsObjectWith(
				getService(domain).getKeyName(),
				getService(domain).getLatestKey()
			)
		);
	}

	@Then("the response array does not contain {string} with value {string}")
	public void verifyElementDoesNotExistInList (String id, String value) {
		assertFalse(getService().lastArrayContainsObjectWith(id, value));
	}

	@Then("the response matches")
	public void verifyElement (DataTable data) {
		assertTrue(getService().lastObjectMatches(data));
	}

	@Then("{string} contains latest {string} in {string}")
	public void verifyDependentDomains (String parent, String child, String element) {
		assertTrue (getResponseBody(), getService(parent).containsKeyInElement(
			element,
			getService(child).getKeyName(),
			getService(child).getLatestKey()
		));
	}
}




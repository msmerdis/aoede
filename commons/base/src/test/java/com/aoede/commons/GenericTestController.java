package com.aoede.commons;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import com.aoede.commons.base.BaseStepDefinition;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;

public class GenericTestController extends BaseStepDefinition {

	@Autowired
	private ListableBeanFactory listableBeanFactory;
	private Map<String, AbstractTestController> controllers = new HashMap<String, AbstractTestController> ();
	private AbstractTestController latestController;

	@PostConstruct
	private void discoverTestControllers () {
		// get all service beans
		var beans = listableBeanFactory.getBeansOfType(AbstractTestController.class, false, false);
		logger.info("looking for test controller implementations in {}", this.getClass().getName());

		for (String name : beans.keySet()) {
			var controller = beans.get(name);
			logger.info("identified service implementation {}", controller.getClass().getName());

			controllers.put(controller.getName(), controller);
		}
	}

	private AbstractTestController getController (String domain) {
		assertTrue("test controller for " + domain + " cannot be found", controllers.containsKey(domain));
		latestController = controllers.get(domain);
		return latestController;
	}

	private AbstractTestController getController () {
		return latestController;
	}

	private String getPath (String domain) {
		return getController(domain).getPath();
	}

	private String getPath (String domain, String path) {
		return getPath(domain) + path;
	}

	@Override
	public void setup (TestCaseStarted event) {
		super.setup(event);

		for (var controller : controllers.values()) {
			controller.setup ();
		}
	}

	@Override
	public void cleanup (TestCaseFinished event) {
		super.cleanup(event);

		for (var controller : controllers.values()) {
			controller.cleanup ();
		}
	}

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
		getController(domain).searchResults(
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
		getController(domain).accessResults(
			getResponseStatus(),
			getResponseHeaders(),
			getResponseBody()
		);
	}

	@When("request all available {string}")
	public void findAll (String domain) {
		logger.info("retrieving all available " + domain);

		executeGet (getPath(domain));

		// update test controller with data
		getController(domain).findAllResults(
			getResponseStatus(),
			getResponseHeaders(),
			getResponseBody()
		);
	}

	@Given("a {string} with")
	public void create(String domain, DataTable data) {
		logger.info("creating " + domain);

		executePost (getPath(domain), getController(domain).createBody(data));

		// update test controller with data
		getController(domain).createResults(
			getResponseStatus(),
			getResponseHeaders(),
			getResponseBody()
		);
	}

	@When("update {string} with id {string}")
	public void update(String domain, String id, DataTable data) {
		logger.info("updating " + domain + " with id " + id);

		executePut (getPath(domain, "/" + id), getController(domain).createBody(data));

		// update test controller with data
		getController(domain).updateResults(
			getResponseStatus(),
			getResponseHeaders(),
			getResponseBody()
		);
	}

	@When("delete {string} with id {string}")
	public void delete(String domain, String id) {
		logger.info("deleting " + domain + " with id " + id);

		executeDelete (getPath(domain, "/" + id));

		// update test controller with data
		getController(domain).deleteResults(
			getResponseStatus(),
			getResponseHeaders(),
			getResponseBody()
		);
	}

	/**
	 * assertions
	 */

	@Then("the request was successful")
	public void verifySuccessfulRequest () {
		assertTrue(getController().isSuccessful());
	}

	@Then("the request was not successful")
	public void verifyNotSuccessfulRequest () {
		assertFalse(getController().isSuccessful());
	}

	@Then("the response has a status code of {int}")
	public void verifyStatus (int code) {
		assertEquals(code, getResponseStatus().value());
	}

	@Then("the response array contains")
	public void verifyElementList (DataTable data) {
		assertTrue(getController().lastArrayMatches(data));
	}

	@Then("the response array contains {string} with value {string}")
	public void verifyElementExistInList (String id, String value) {
		assertTrue(getController().lastArrayContainsObjectWith(id, value));
	}

	@Then("the response array does not contain {string} with value {string}")
	public void verifyElementDoesNotExistInList (String id, String value) {
		assertFalse(getController().lastArrayContainsObjectWith(id, value));
	}

	@Then("the response matches")
	public void verifyElement (DataTable data) {
		assertTrue(getController().lastObjectMatches(data));
	}
}




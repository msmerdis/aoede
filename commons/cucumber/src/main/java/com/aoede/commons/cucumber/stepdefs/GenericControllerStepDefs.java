package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpHeaders;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.ResponseResults;
import com.aoede.commons.cucumber.service.AbstractTestService;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.JsonService;
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

	/**
	 * Data preparation steps
	 */

	@When("prepare composite id {string}")
	public void prepareCompositeId(String name, DataTable data) {
		jsonService.putCompositeKey(name, data);
	}

	@When("prepare json {string}")
	public void prepareJsonObject(String name, DataTable data) {
		jsonService.putObject(name, data);
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

	/**
	 * Requests
	 */

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

	///////////////////////////////////////////////////////////////////////////////////////////////
	// CREATE

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

	///////////////////////////////////////////////////////////////////////////////////////////////
	// UPDATE

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

	///////////////////////////////////////////////////////////////////////////////////////////////
	// DELETE

	@When("delete {string} with id {string}")
	public void deleteSimple(String domain, String id) {
		logger.info("deleting " + domain + " with id " + id);
		ResponseResults results = executeDelete (services.getPathForService(domain, "/" + id));

		// update test controller with data
		services.getLatestService(domain).deleteResults(results);
	}

	@When("delete {string} with composite id {string}")
	public void deleteCompositePrepared(String domain, String name) {
		String id = jsonService.getCompositeKey(name);

		assertNotNull(id, "composite id has not been prepared");

		deleteSimple(domain, id);
	}

	@When("delete {string} with composite id")
	public void deleteComposite(String domain, DataTable data) {
		deleteSimple(domain, jsonService.generateCompositeKey(data));
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

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Status checks

	@Then("the request was successful")
	public void verifySuccessfulRequest () {
		verifySuccessfulRequest(services.getLatestService());
	}

	@Then("the {string} request was successful")
	public void verifySuccessfulRequest (String domain) {
		verifySuccessfulRequest(services.getService(domain));
	}

	private void verifySuccessfulRequest(AbstractTestService service) {
		assertTrue("request was not successful", service.isSuccess());
	}

	@Then("the request was not successful")
	public void verifyNotSuccessfulRequest () {
		verifyNotSuccessfulRequest(services.getLatestService());
	}

	@Then("the {string} request was not successful")
	public void verifyNotSuccessfulRequest (String domain) {
		verifyNotSuccessfulRequest(services.getService(domain));
	}

	private void verifyNotSuccessfulRequest(AbstractTestService service) {
		assertFalse("request was successful", service.isSuccess());
	}

	@Then("the response has a status code of {int}")
	public void verifyStatus (int code) {
		verifyStatus(services.getLatestService(), code);
	}

	@Then("the {string} response has a status code of {int}")
	public void verifyStatus (String domain, int code) {
		verifyStatus(services.getService(domain), code);
	}

	private void verifyStatus (AbstractTestService service, int code) {
		assertEquals(code, service.getLatestResults().status.value());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Array response checks

	@Then("the response array contains {string} objects")
	public void verifyArrayList (String dataTableName, DataTable data) {
		verifyArrayList(services.getLatestService(), dataTableName, data);
	}

	@Then("the {string} response array contains {string} objects")
	public void verifyArrayList (String domain, String dataTableName, DataTable data) {
		verifyArrayList(services.getService(domain), dataTableName, data);
	}

	private void verifyArrayList (AbstractTestService service, String dataTableName, DataTable data) {
		// generate a json array using a previously stored template
		JsonArray array = jsonService.generateJsonArray(
			dataTableService.get(dataTableName), data);

		assertNotNull("could not generate json array", array);

		assertTrue(
			jsonService.jsonArrayMatches(service.getLatestArr(), array)
		);
	}

	@Then("the response array contains {string} with {string} value {string}")
	public void verifyElementExistInList (String id, String type, String value) {
		assertTrue(
			jsonService.jsonArrayContainsObject(services.getLatestService().getLatestArr(), id, type, value)
		);
	}

	@Then("the response array does not contain {string} with {string} value {string}")
	public void verifyElementDoesNotExistInList (String id, String type, String value) {
		assertFalse(
			jsonService.jsonArrayContainsObject(services.getLatestService().getLatestArr(), id, type, value)
		);
	}

	@Then("the response array contains latest {string}")
	public void verifyLatestElementExistsInList (String domain) {
		var latestService = services.getLatestService();

		assertTrue (
			jsonService.jsonArrayContainsObjectWithElement(
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
			jsonService.jsonArrayContainsObjectWithElement(
				latestService.getLatestArr(),
				latestService.getKeyName(),
				latestService.getLatestKey()
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

	@Then("{string} contains latest {string} in {string}")
	public void verifyDependentDomains (String parent, String child, String element) {
		AbstractTestService pService = services.getService(parent);
		AbstractTestService cService = services.getService(child);

		JsonObject obj = pService.getLatestObj();

		assertTrue (parent + " does not have element " + element, obj.has(element));
		assertTrue (element + " is not an array", obj.get(element).isJsonArray());

		assertTrue (
			jsonService.jsonArrayContainsObjectWithElement(
				obj.get(element).getAsJsonArray(),
				cService.getKeyName(),
				cService.getLatestKey()
			)
		);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Object response checks

	@Then("the response contains {string} objects in {string}")
	public void verifyElementList (String dataTableName, String element, DataTable data) {
		verifyElementList (services.getLatestService(), dataTableName, element, data);
	}

	@Then("the {string} response contains {string} objects in {string}")
	public void verifyElementList (String domain, String dataTableName, String element, DataTable data) {
		verifyElementList (services.getService(domain), dataTableName, element, data);
	}

	private void verifyElementList (AbstractTestService service, String dataTableName, String element, DataTable data) {
		// generate a json array using a previously stored template
		JsonArray array = jsonService.generateJsonArray(
			dataTableService.get(dataTableName), data);

		assertNotNull("could not generate json array", array);

		JsonObject obj = service.getLatestObj();

		assertTrue ("does not have element " + element, obj.has(element));
		assertTrue (element + " is not an array", obj.get(element).isJsonArray());

		assertTrue(
			jsonService.jsonArrayMatches(obj.get(element).getAsJsonArray(), array)
		);
	}

	@Then("the response matches")
	public void verifyElement (DataTable data) {
		verifyElement(services.getLatestService(), data);
	}

	@Then("the {string} response matches")
	public void verifyElement (String domain, DataTable data) {
		verifyElement(services.getService(domain), data);
	}

	private void verifyElement (AbstractTestService service, DataTable data) {
		assertTrue(
			jsonService.jsonObjectMatches(
				service.getLatestObj(), data)
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

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Response headers checks

	@Then("the response returned header {string}")
	public void verifyHeader (String header) {
		verifyHeader (services.getLatestService(), header);
	}

	@Then("the {string} response returned header {string}")
	public void verifyHeader (String domain, String header) {
		verifyHeader (services.getService(domain), header);
	}

	private void verifyHeader (AbstractTestService service, String header) {
		assertTrue(
			"header " + header + " not found in response",
			service.getLatestResults().headers.containsKey(header)
		);
	}

	@Then("the response header {string} contains {string}")
	public void matchHeader (String header, String value) {
		matchHeader (services.getLatestService(), header, value);
	}

	@Then("the {string} response header {string} contains {string}")
	public void matchHeader (String domain, String header, String value) {
		matchHeader (services.getService(domain), header, value);
	}

	private void matchHeader (AbstractTestService service, String header, String value) {
		verifyHeader(service, header);

		assertTrue(
			"header " + header + " not found in response",
			service.getLatestResults().headers.get(header).contains(value)
		);
	}
}




package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertTrue;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.HttpService;
import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.cucumber.java.en.When;

public class DataManipulationStepDefinitions extends BaseStepDefinition {

	public DataManipulationStepDefinitions (
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

	@When("set {string} object's {string} element to {string} as {string}")
	public void addElementToObject(String json, String element, String value, String type) {
		JsonObject obj = jsonService.getObject(json);

		obj.add(element, jsonService.generateJsonElement(type, value));
	}

	@When("remove {string} element from {string}")
	public void removeElementFromObject(String element, String json) {
		JsonObject obj = jsonService.getObject(json);

		assertTrue("json " + json + " does not have element " + element, obj.has(element));

		obj.remove(element);
	}

	@When("add {string} to {string} array as {string}")
	public void addElementToArray(String value, String json, String type) {
		JsonArray arr = jsonService.getArray(json);

		arr.add(jsonService.generateJsonElement(type, value));
	}

	@When("remove element {int} from {string} array")
	public void removeElementFromArray(int element, String json) {
		JsonArray arr = jsonService.getArray(json);

		assertTrue("json " + json + " does not have element " + element, 0 <= element && element < arr.size());

		arr.remove(element);
	}

	@When("store {string} element from {string} object as {string}")
	public void extractElementFromObject(String element, String json, String name) {
		JsonObject obj = jsonService.getObject(json);

		assertTrue("json " + json + " does not have element " + element, obj.has(element));

		jsonService.put(name, obj.get(element));
	}

	@When("store element {int} from {string} array as {string}")
	public void extractElementFromArray(int element, String json, String name) {
		JsonArray arr = jsonService.getArray(json);

		assertTrue("json " + json + " does not have element " + element, 0 <= element && element < arr.size());

		jsonService.put(name, arr.get(element));
	}

	@When("copy {string} json as {string}")
	public void copyJson(String json, String copy) {
		assertTrue("json " + json + " was not found", jsonService.containsKey(json));

		jsonService.put(copy, jsonService.get(json).deepCopy());
	}

}




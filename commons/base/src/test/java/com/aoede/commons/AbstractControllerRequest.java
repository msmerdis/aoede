package com.aoede.commons;

import org.springframework.http.HttpHeaders;

import com.aoede.commons.base.BaseStepDefinition;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class AbstractControllerRequest extends BaseStepDefinition {

	@When("search {string} with keyword {string}")
	public void search(String domain, String keyword) {
		logger.info("searching " + domain + " for " + keyword);
		HttpHeaders headers = defaultHeaders();

		headers.add("X-Search-Terms", keyword);

		executeGet ("/api/" + domain + "/search", headers);
	}

	@When("request a {string} with id {string}")
	public void get(String domain, String id) {
		logger.info("searching " + domain + " for keyword");

		executeGet ("/api/" + domain + "/" + id);
	}

	@When("request all available {string}")
	public void findAll (String domain) {
		logger.info("retrieving all available " + domain);

		executeGet ("/api/" + domain);
	}

	@Given("a {string} with")
	public void create(String domain, DataTable data) {
		logger.info("creating " + domain);
		JsonObject obj = new JsonObject();

		for (var row : data.asLists()) {
			obj.add(row.get(0), new JsonPrimitive(row.get(1)));
		}

		executePost ("/api/" + domain, obj.toString());
	}

	@When("update {string} with id {string}")
	public void update(String domain, String id, DataTable data) {
		logger.info("updating " + domain + " with id " + id);
		JsonObject obj = new JsonObject();

		for (var row : data.asLists()) {
			obj.add(row.get(0), new JsonPrimitive(row.get(1)));
		}

		executePut ("/api/" + domain + "/" + id, obj.toString());
	}

	@When("delete {string} with id {string}")
	public void delete(String domain, String id) {
		logger.info("deleting " + domain + " with id " + id);

		executeDelete ("/api/" + domain + "/" + id);
	}
}




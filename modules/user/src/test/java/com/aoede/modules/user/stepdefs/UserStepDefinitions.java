package com.aoede.modules.user.stepdefs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.ResponseResults;
import com.aoede.commons.cucumber.service.AbstractTestService;
import com.aoede.modules.user.service.LoginTestService;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UserStepDefinitions extends BaseStepDefinition {

	@Autowired
	private LoginTestService loginTestService;

	/**
	 * Requests
	 */

	@When("update latest users password to {string}")
	public void updatePassword(String password) {
		logger.info("updating users password to " + password);
		AbstractTestService service = services.getService("user");
		JsonObject object = new JsonObject ();

		object.add("password", new JsonPrimitive(password));

		ResponseResults results = executePut(
			service.getPath() + "/" + service.getLatestKey() + "/password",
			object.toString()
		);

		service.updateResults(results);
	}

	@When("{string} attempts to login with password {string}")
	public void login(String username, String password) {
		logger.info("login user " + username + " with password " + password);

		JsonObject object = new JsonObject ();

		object.add("username", new JsonPrimitive(username));
		object.add("password", new JsonPrimitive(password));

		ResponseResults results = executePost("/login", object.toString());

		loginTestService.results(results);
	}

	/**
	 * assertions
	 */

	@Then("login is successful")
	public void loginSuccess() {
		logger.info("verify user was able to login");

		assertTrue(loginTestService.isSuccess());
	}

	@Then("login has failed")
	public void loginFailure() {
		logger.info("verify user was not able to login");

		assertFalse(loginTestService.isSuccess());
	}

	@Then("login results match")
	public void verifyElement (DataTable data) {
		assertTrue(
			jsonObjectService.jsonObjectMatches(
				loginTestService.getUserDetails(), data)
		);
	}

}




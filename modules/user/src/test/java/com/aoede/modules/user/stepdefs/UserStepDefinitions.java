package com.aoede.modules.user.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.ResponseResults;
import com.aoede.commons.cucumber.service.AbstractTestService;
import com.aoede.modules.user.service.LoginTestService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UserStepDefinitions extends BaseStepDefinition {

	@Autowired
	private LoginTestService loginTestService;

	/**
	 * Requests
	 */

	@When("request user with username {string}")
	public void getByUsername(String username) {
		logger.info("get user with username " + username);
		AbstractTestService service = services.getLatestService("user");

		ResponseResults results = executeGet(
			service.getPath() + "/username/" + username
		);

		service.accessResults(results);
	}

	@When("update latest users password to {string}")
	public void updatePassword(String password) {
		logger.info("updating users password to " + password);
		AbstractTestService service = services.getLatestService("user");
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

	@When("check user status")
	public void status() {
		logger.info("check user status");

		ResponseResults results = executeGet("/login");

		loginTestService.results(results);
	}

	@Given("a logged in user {string} with password {string}")
	public void ensureUserIsLoggedin(String username, String password) {
		getByUsername (username);

		var userService = services.getLatestService();
		JsonObject user = null;

		assertNotNull("user service was not found", userService);

		// if user not found create it
		if (userService.isSuccess()) {
			user = userService.getLatestObj();
		} else {
			user = createUser (username, password, userService);
		}

		// update status to active if its not
		if (!user.get("status").getAsString().equals("ACTIVE")) {
			updateStatus(user.get(userService.getKeyName()).getAsLong(), userService);
		}

		// finally try to login the user
		login(username, password);

		// if not successful, change the password and try again
		if (!loginTestService.isSuccess()) {
			updatePassword(password);
			login(username, password);
		}
	}

	private JsonObject createUser (String username, String password, AbstractTestService service) {
		JsonObject object = new JsonObject();

		object.add("status", new JsonPrimitive("ACTIVE"));
		object.add("username", new JsonPrimitive(username));
		object.add("password", new JsonPrimitive(password));

		ResponseResults results = executePost (service.getPath(), object.toString());

		assertEquals("creating user was not successful", 201, results.status.value());
		assertNotNull("user creation did not contain a body", results.body);

		JsonElement body = JsonParser.parseString(results.body);

		assertTrue ("user creation results is not an object", body.isJsonObject());

		return body.getAsJsonObject();
	}

	private void updateStatus (long id, AbstractTestService service) {
		JsonObject object = new JsonObject();

		object.add("status", new JsonPrimitive("ACTIVE"));

		ResponseResults results = executePut (service.getPath() + "/" + id, object.toString());

		assertEquals("updating user status was not successful", 204, results.status.value());
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




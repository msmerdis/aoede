package com.aoede.modules.user.stepdefs;

import com.aoede.commons.base.BaseStepDefinition;
import com.aoede.commons.base.ResponseResults;
import com.aoede.commons.service.AbstractTestService;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.java.en.When;

public class UserStepDefinitions extends BaseStepDefinition {

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
}




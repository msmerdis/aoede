package com.aoede.commons;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aoede.commons.base.BaseStepDefinition;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import junit.framework.AssertionFailedError;

public class AbstractControllerAssertions extends BaseStepDefinition {
	@Then("the response has a status code of {int}")
	public void verifyStatus (int code) {
		assertEquals(code, getResponseStatus().value());
	}

	@Then("the response array contains")
	public void verifyElementList (DataTable data) {
		JsonArray body = JsonParser.parseString(getResponseBody()).getAsJsonArray();

		assertTrue(verifyAllElementsExist (body, data.asMaps()));
	}

	@Then("the response array does not contain {string} with value {string}")
	public void verifyElementDoesNotExistInList (String id, String value) {
		JsonArray body = JsonParser.parseString(getResponseBody()).getAsJsonArray();

		for (var element : body) {
			if (element.getAsJsonObject().get(id).getAsString().equals(value))
				throw new AssertionFailedError("Element " + id + " with value " + value + " found in object : " + element.toString());
		}
	}

	@Then("the response matches")
	public void verifyElement (DataTable data) {
		Map<String, String> element = new HashMap<String, String> ();

		for (var row : data.asLists()) {
			element.put(row.get(0), row.get(1));
		}

		assertTrue (verifyElement (JsonParser.parseString(getResponseBody()).getAsJsonObject(), element.entrySet()));
	}

	private boolean verifyAllElementsExist (JsonArray body, List<Map<String, String>> data) {
		for (var tableRow : data) {
			if (!verifyElementExists (body, tableRow)) {
				return false;
			}
		}
		return true;
	}

	private boolean verifyElementExists (JsonArray body, Map<String, String> result) {
		// Iterate all elements in the body and verify if any of the elements match the result
		logger.debug("arg check " + body);
		for (var element : body) {
			JsonObject obj = element.getAsJsonObject();

			if (verifyElement (obj, result.entrySet())) {
				logger.info("matched " + result + " against " + obj);
				return true;
			}
		}

		logger.info(result + " could not be matched");
		return false;
	}

	private boolean verifyElement (JsonObject obj, Set<Map.Entry<String, String>> values) {
		logger.debug("matching " + obj + " against " + values);
		for (var value : values) {
			if (!obj.has(value.getKey())) {
				logger.debug(obj + " does not have element " + value.getKey());
				return false;
			}
			if (!obj.get(value.getKey()).getAsString().equals(value.getValue())) {
				logger.debug(obj.get(value.getKey()).getAsString() + " does not match " + value.getValue());
				return false;
			}
		}
		logger.debug("match found");
		return true;
	}
}

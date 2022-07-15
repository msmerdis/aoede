package com.aoede.commons.cucumber.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.aoede.JsonServiceImplTestCaseSetup;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

public class TestJsonServiceImplJsonObjectMatches extends JsonServiceImplTestCaseSetup {

	@Test
	public void verifyJsonMatchSuccessWithDataTable () throws Exception {
		DataTable expected = buildDataTable (List.of(
			List.of( "id" , "integer", "1")
		));

		JsonObject object = new JsonObject ();

		object.add("id", new JsonPrimitive(1));
		object.add("name", new JsonPrimitive("hello"));

		assertTrue ("json is not matched correctly", uut().jsonObjectMatches(object, expected));
	}

	// the other way around does not work
	// all items in the table data must in in the json object
	@Test
	public void verifyJsonMatchFailureWithDataTable () throws Exception {
		DataTable expected = buildDataTable (List.of(
			List.of( "id" , "integer", "1"),
			List.of("name", "string", "hello")
		));

		JsonObject object = new JsonObject ();

		object.add("id", new JsonPrimitive(1));

		assertFalse ("json is not matched correctly", uut().jsonObjectMatches(object, expected));
	}

	@Test
	public void verifyJsonMatchPrimitive () throws Exception {
		JsonObject expected = new JsonObject ();

		expected.add("id", new JsonPrimitive(1));
		expected.add("name", new JsonPrimitive("hello"));
		expected.add("bool", new JsonPrimitive(true));

		JsonObject actual = new JsonObject ();

		actual.add("id", new JsonPrimitive(1));
		actual.add("name", new JsonPrimitive("hello"));
		actual.add("bool", new JsonPrimitive(true));
		actual.add("extra", new JsonPrimitive(false));

		assertTrue ("json is not matched correctly", uut().jsonObjectMatches(actual, expected));
		assertFalse ("json is not matched correctly", uut().jsonObjectMatches(expected, actual));
	}

	@Test
	public void verifyJsonDontMatchPrimitiveMissingKey () throws Exception {
		JsonObject expected = new JsonObject ();

		expected.add("id", new JsonPrimitive(1));
		expected.add("name", new JsonPrimitive("hello"));
		expected.add("bool", new JsonPrimitive(true));

		JsonObject actual = new JsonObject ();

		actual.add("id", new JsonPrimitive(1));
		actual.add("name", new JsonPrimitive("hello"));
		actual.add("extra", new JsonPrimitive(false));

		assertFalse ("json is not matched correctly", uut().jsonObjectMatches(actual, expected));
		assertFalse ("json is not matched correctly", uut().jsonObjectMatches(expected, actual));
	}

	@Test
	public void verifyJsonDontMatchPrimitiveValueMismatch () throws Exception {
		JsonObject expected = new JsonObject ();

		expected.add("id", new JsonPrimitive(1));
		expected.add("name", new JsonPrimitive("hello!"));
		expected.add("bool", new JsonPrimitive(true));

		JsonObject actual = new JsonObject ();

		actual.add("id", new JsonPrimitive(1));
		actual.add("name", new JsonPrimitive("hello"));
		actual.add("bool", new JsonPrimitive(true));
		actual.add("extra", new JsonPrimitive(false));

		assertFalse ("json is not matched correctly", uut().jsonObjectMatches(actual, expected));
		assertFalse ("json is not matched correctly", uut().jsonObjectMatches(expected, actual));
	}

	@Test
	public void verifyJsonMatchNull () throws Exception {
		JsonObject expected = new JsonObject ();

		expected.add("null", JsonNull.INSTANCE);

		JsonObject actual = new JsonObject ();

		actual.add("id", new JsonPrimitive(1));
		actual.add("null", JsonNull.INSTANCE);

		assertTrue ("json is not matched correctly", uut().jsonObjectMatches(actual, expected));
		assertFalse ("json is not matched correctly", uut().jsonObjectMatches(expected, actual));
	}

	@Test
	public void verifyJsonDontMatchNullMissingKey () throws Exception {
		JsonObject expected = new JsonObject ();

		expected.add("null1", JsonNull.INSTANCE);

		JsonObject actual = new JsonObject ();

		actual.add("id", new JsonPrimitive(1));
		actual.add("null2", JsonNull.INSTANCE);

		assertFalse ("json is not matched correctly", uut().jsonObjectMatches(actual, expected));
		assertFalse ("json is not matched correctly", uut().jsonObjectMatches(expected, actual));
	}

	@Test
	public void verifyJsonDontMatchNullValueMismatch () throws Exception {
		JsonObject expected = new JsonObject ();

		expected.add("null", new JsonPrimitive("null"));

		JsonObject actual = new JsonObject ();

		actual.add("id", new JsonPrimitive(1));
		actual.add("null", JsonNull.INSTANCE);

		assertFalse ("json is not matched correctly", uut().jsonObjectMatches(actual, expected));
		assertFalse ("json is not matched correctly", uut().jsonObjectMatches(expected, actual));
	}

	@Test
	public void verifyJsonMatchObject () throws Exception {
		JsonObject expected = new JsonObject ();
		JsonObject expectedInner = new JsonObject ();

		expectedInner.add("innerVal", new JsonPrimitive("two"));

		expected.add("data", expectedInner);

		JsonObject actual = new JsonObject ();
		JsonObject actualInner = new JsonObject ();

		actualInner.add("innerKey", new JsonPrimitive(2));
		actualInner.add("innerVal", new JsonPrimitive("two"));

		actual.add("id", new JsonPrimitive(1));
		actual.add("data", actualInner);

		assertTrue ("json is not matched correctly", uut().jsonObjectMatches(actual, expected));
		assertFalse ("json is not matched correctly", uut().jsonObjectMatches(expected, actual));
	}

	@Test
	public void verifyJsonMatchArray () throws Exception {
		JsonObject expected = new JsonObject ();
		JsonArray expectedInner = new JsonArray ();

		expectedInner.add(new JsonPrimitive(5));

		expected.add("data", expectedInner);

		JsonObject actual = new JsonObject ();
		JsonArray actualInner = new JsonArray ();

		actualInner.add(new JsonPrimitive(1));
		actualInner.add(new JsonPrimitive(3));
		actualInner.add(new JsonPrimitive(5));
		actualInner.add(new JsonPrimitive(7));
		actualInner.add(new JsonPrimitive(9));

		actual.add("data", actualInner);

		assertTrue ("json is not matched correctly", uut().jsonObjectMatches(actual, expected));
		assertFalse ("json is not matched correctly", uut().jsonObjectMatches(expected, actual));
	}

	@Test
	public void verifyJsonDontMatchArrayContents () throws Exception {
		JsonObject expected = new JsonObject ();
		JsonArray expectedInner = new JsonArray ();

		expectedInner.add(new JsonPrimitive(6));

		expected.add("data", expectedInner);

		JsonObject actual = new JsonObject ();
		JsonArray actualInner = new JsonArray ();

		actualInner.add(new JsonPrimitive(1));
		actualInner.add(new JsonPrimitive(3));
		actualInner.add(new JsonPrimitive(5));
		actualInner.add(new JsonPrimitive(7));
		actualInner.add(new JsonPrimitive(9));

		actual.add("data", actualInner);

		assertFalse ("json is not matched correctly", uut().jsonObjectMatches(actual, expected));
		assertFalse ("json is not matched correctly", uut().jsonObjectMatches(expected, actual));
	}

}




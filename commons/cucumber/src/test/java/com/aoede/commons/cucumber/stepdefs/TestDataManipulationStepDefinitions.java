package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import com.aoede.DataManipulationStepDefinitionsTestCaseSetup;
import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.service.JsonServiceImpl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class TestDataManipulationStepDefinitions extends DataManipulationStepDefinitionsTestCaseSetup {

	private JsonServiceImpl jsonServiceImpl;

	@Override
	protected DataManipulationStepDefinitions uut () throws Exception {
		var uut = super.uut();

		// use actual json object service for the test
		jsonServiceImpl = new JsonServiceImpl(null);
		setField ((BaseStepDefinition)uut, "jsonService", jsonServiceImpl);

		return uut;
	}

	@Test
	public void verifyAddElementToEmptyObjectSuccess() throws Exception {
		var uut = uut ();

		jsonServiceImpl.put("json", new JsonObject());

		// execute function
		uut.addElementToObject("json", "count", "4", "int");

		assertEquals("element was not added correctly", "{\"count\":4}", jsonServiceImpl.get("json").toString());
	}

	@Test
	public void verifyAddElementToObjectSuccess() throws Exception {
		var uut = uut ();
		var obj = new JsonObject();

		obj.add("n1", new JsonPrimitive("v1"));
		obj.add("n2", new JsonPrimitive("v2"));

		jsonServiceImpl.put("json", obj);

		// execute function
		uut.addElementToObject("json", "n3", "v3", "string");

		assertEquals(
			"element was not added correctly",
			"{\"n1\":\"v1\",\"n2\":\"v2\",\"n3\":\"v3\"}",
			jsonServiceImpl.get("json").toString()
		);
	}

	@Test
	public void verifyOverrideElementToObjectSuccess() throws Exception {
		var uut = uut ();
		var obj = new JsonObject();

		obj.add("n1", new JsonPrimitive(1));
		obj.add("n2", new JsonPrimitive(2));

		jsonServiceImpl.put("json", obj);

		// execute function
		uut.addElementToObject("json", "n2", "3", "int");

		assertEquals(
			"element was not added correctly",
			"{\"n1\":1,\"n2\":3}",
			jsonServiceImpl.get("json").toString()
		);
	}

	@Test
	public void verifyOverrideElementToIncorrectObjectFailure() throws Exception {
		var uut = uut ();
		var arr = new JsonArray();

		jsonServiceImpl.put("json", arr);

		assertThrows ("should not be able to add an element to an array", AssertionError.class, () -> {
			uut.addElementToObject("json", "n2", "3", "int");
		});
	}

	@Test
	public void verifyAddElementToObjectFailure() throws Exception {
		var uut = uut ();

		assertThrows ("should not be able to add an element to a non existing json", AssertionError.class, () -> {
			uut.addElementToObject("name", "json", "null", "");
		});
	}

	@Test
	public void verifyRemoveElementFromObjectSuccess() throws Exception {
		var uut = uut ();
		var obj = new JsonObject();

		obj.add("n1", new JsonPrimitive("v1"));
		obj.add("n2", new JsonPrimitive("v2"));

		jsonServiceImpl.put("json", obj);

		// execute function
		uut.removeElementFromObject("n1", "json");

		assertEquals(
			"element was not added correctly",
			"{\"n2\":\"v2\"}",
			jsonServiceImpl.get("json").toString()
		);
	}

	@Test
	public void verifyRemoveElementFromEmptyObjectFailure() throws Exception {
		var uut = uut ();

		jsonServiceImpl.put("json", new JsonObject());

		assertThrows ("should not be able to remove an element that does not exist", AssertionError.class, () -> {
			uut.removeElementFromObject("n1", "json");
		});
	}

	@Test
	public void verifyRemoveElementFromIncorrectObjectFailure() throws Exception {
		var uut = uut ();

		jsonServiceImpl.put("json", new JsonArray());

		assertThrows ("should not be able to remove an element from an array", AssertionError.class, () -> {
			uut.removeElementFromObject("n1", "json");
		});
	}

	@Test
	public void verifyRemoveElementFromObjectFailure() throws Exception {
		var uut = uut ();

		assertThrows ("should not be able to remove an element from a non existing json", AssertionError.class, () -> {
			uut.removeElementFromObject("n1", "json");
		});
	}

	@Test
	public void verifyAddElementToArraySuccess() throws Exception {
		var uut = uut ();

		jsonServiceImpl.put("json", new JsonArray());

		// execute function
		uut.addElementToArray("1", "json", "int");
		uut.addElementToArray("2", "json", "int");

		assertEquals("element was not added correctly", "[1,2]", jsonServiceImpl.get("json").toString());
	}

	@Test
	public void verifyAddElementToArrayDublicateSuccess() throws Exception {
		var uut = uut ();

		jsonServiceImpl.put("json", new JsonArray());

		// execute function
		uut.addElementToArray("1", "json", "int");
		uut.addElementToArray("1", "json", "int");
		uut.addElementToArray("1", "json", "int");

		assertEquals("element was not added correctly", "[1,1,1]", jsonServiceImpl.get("json").toString());
	}

	@Test
	public void verifyAddElementToArrayFailure() throws Exception {
		var uut = uut ();

		assertThrows ("should not be able to remove an element from a non existing json", AssertionError.class, () -> {
			uut.addElementToArray("1", "json", "int");
		});
	}

	@Test
	public void verifyRemoveElementFromArraySuccess() throws Exception {
		var uut = uut ();
		var arr = new JsonArray();

		arr.add(new JsonPrimitive("v1"));
		arr.add(new JsonPrimitive("v2"));
		arr.add(new JsonPrimitive("v3"));

		jsonServiceImpl.put("json", arr);

		// execute function
		uut.removeElementFromArray(1, "json");

		assertEquals(
			"element was not added correctly",
			"[\"v1\",\"v3\"]",
			jsonServiceImpl.get("json").toString()
		);
	}

	@Test
	public void verifyRemoveElementFromArrayOutOfBoundsFailure() throws Exception {
		var uut = uut ();
		var arr = new JsonArray();

		arr.add(new JsonPrimitive("v1"));
		arr.add(new JsonPrimitive("v2"));
		arr.add(new JsonPrimitive("v3"));

		jsonServiceImpl.put("json", arr);

		assertThrows ("cannot remove items les than 0", AssertionError.class, () -> {
			uut.removeElementFromArray(-1, "json");
		});

		assertThrows ("cannot remove items greater or equal to the array size", AssertionError.class, () -> {
			uut.removeElementFromArray(3, "json");
		});
	}

	@Test
	public void verifyRemoveElementFromMissingArrayFailure() throws Exception {
		var uut = uut ();

		assertThrows ("cannot remove items from a non existing json", AssertionError.class, () -> {
			uut.removeElementFromArray(0, "json");
		});
	}

	@Test
	public void verifyExtractElementFromObjectSuccess() throws Exception {
		var uut = uut ();
		var obj = new JsonObject();
		var arr = new JsonArray();

		arr.add(1);
		arr.add(2);
		arr.add(3);

		obj.add("n1", new JsonPrimitive("v1"));
		obj.add("n2", arr);

		jsonServiceImpl.put("json", obj);

		// execute function
		uut.extractElementFromObject("n2", "json", "name");

		assertEquals(
			"element was not extracted correctly",
			"[1,2,3]",
			jsonServiceImpl.get("name").toString()
		);
	}

	@Test
	public void verifyExtractNonExistingElementFromObjectFailure() throws Exception {
		var uut = uut ();
		var obj = new JsonObject();

		obj.add("n1", new JsonPrimitive("v1"));
		obj.add("n2", new JsonPrimitive("v2"));

		jsonServiceImpl.put("json", obj);

		assertThrows ("cannot extract non existing elements", AssertionError.class, () -> {
			uut.extractElementFromObject("n3", "json", "name");
		});
	}

	@Test
	public void verifyExtractElementFromMissingObjectFailure() throws Exception {
		var uut = uut ();

		assertThrows ("cannot extract elements from non existing json", AssertionError.class, () -> {
			uut.extractElementFromObject("n3", "json", "name");
		});
	}

	@Test
	public void verifyExtractElementFromArraySuccess() throws Exception {
		var uut = uut ();
		var arr = new JsonArray();
		var obj = new JsonObject();

		obj.add("n1", new JsonPrimitive(1));
		obj.add("n2", new JsonPrimitive(2));

		arr.add(obj);

		jsonServiceImpl.put("json", arr);

		// execute function
		uut.extractElementFromArray(0, "json", "name");

		assertEquals(
			"element was not extracted correctly",
			"{\"n1\":1,\"n2\":2}",
			jsonServiceImpl.get("name").toString()
		);
	}

	@Test
	public void verifyExtractNonExistingElementFromArrayFailure() throws Exception {
		var uut = uut ();
		var arr = new JsonArray();
		var obj = new JsonObject();

		obj.add("n1", new JsonPrimitive(1));
		obj.add("n2", new JsonPrimitive(2));

		arr.add(obj);

		jsonServiceImpl.put("json", arr);

		assertThrows ("cannot extract non existing elements", AssertionError.class, () -> {
			uut.extractElementFromArray(1, "json", "name");
		});

		assertThrows ("cannot extract non existing elements", AssertionError.class, () -> {
			uut.extractElementFromArray(-1, "json", "name");
		});
	}

	@Test
	public void verifyExtractElementFromMissingArrayFailure() throws Exception {
		var uut = uut ();

		assertThrows ("cannot extract elements from non existing json", AssertionError.class, () -> {
			uut.extractElementFromArray(0, "json", "name");
		});
	}

	@Test
	public void verifyCopyJson() throws Exception {
		var uut = uut ();
		var val = new JsonObject();

		jsonServiceImpl.put("json", val);

		uut.copyJson("json", "new");

		val.add("test", new JsonPrimitive(1));

		assertEquals(
			"element was not copied correctly",
			"{}",
			jsonServiceImpl.get("new").toString()
		);
	}

}




package com.aoede.commons.cucumber.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import com.aoede.commons.cucumber.service.generators.BigIntegerGenerator;
import com.aoede.commons.cucumber.service.generators.BooleanGenerator;
import com.aoede.commons.cucumber.service.generators.CompositeIdGenerator;
import com.aoede.commons.cucumber.service.generators.FractionGenerator;
import com.aoede.commons.cucumber.service.generators.IntegerGenerator;
import com.aoede.commons.cucumber.service.generators.JsonGenerator;
import com.aoede.commons.cucumber.service.generators.LongGenerator;
import com.aoede.commons.cucumber.service.generators.NullGenerator;
import com.aoede.commons.cucumber.service.generators.RandomStringGenerator;
import com.aoede.commons.cucumber.service.generators.ServiceElementGenerator;
import com.aoede.commons.cucumber.service.generators.StringGenerator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

@Service
public class JsonServiceImpl extends TestStorageServiceImpl<JsonElement> implements JsonService {

	private Map<String, JsonElementGenerator> generators = new HashMap<String, JsonElementGenerator>();

	public JsonServiceImpl (
		AbstractTestServiceDiscoveryService abstractTestServiceDiscoveryService
	) {
		this.generators.put("integer",  new IntegerGenerator());
		this.generators.put("long",     new LongGenerator());
		this.generators.put("number",   new BigIntegerGenerator());
		this.generators.put("string",   new StringGenerator());
		this.generators.put("boolean",  new BooleanGenerator());
		this.generators.put("fraction", new FractionGenerator());

		this.generators.put("compositeId", new CompositeIdGenerator(this));

		this.generators.put("key",    new ServiceElementGenerator(abstractTestServiceDiscoveryService, "key"));
		this.generators.put("object", new ServiceElementGenerator(abstractTestServiceDiscoveryService, "object"));
		this.generators.put("array",  new ServiceElementGenerator(abstractTestServiceDiscoveryService, "array"));

		this.generators.put("random string", new RandomStringGenerator(new Random (System.currentTimeMillis())));

		this.generators.put("json", new JsonGenerator(this));
		this.generators.put("null", new NullGenerator());

		// register aliases
		this.generators.put("int",  this.generators.get("integer"));
		this.generators.put("bool", this.generators.get("boolean"));
		this.generators.put("obj",  this.generators.get("object"));
		this.generators.put("arr",  this.generators.get("array"));
	}

	@Override
	public String generateBase64 (String data) {
		return Base64.encodeBase64String(data.getBytes());
	}

	@Override
	public String generatePaddedBase64 (String data) {
		switch (data.length() % 3) {
			case 1: data = data + "  "; break;
			case 2: data = data + " ";  break;
			default:
				// no padding is needed
		}
		return generateBase64 (data);
	}

	@Override
	public String generateCompositeKey (DataTable data) {
		return generatePaddedBase64(generateJsonObject(data).toString());
	}

	@Override
	public String putCompositeKey(String name, DataTable data) {
		JsonElement previous = put(name, new JsonPrimitive(generateCompositeKey(data)));
		return previous == null ? null : previous.getAsString();
	}

	@Override
	public String getCompositeKey(String name) {
		isCompositeKey(name);
		return get(name).getAsString();
	}

	private boolean isCompositeKey (String name) {
		return
			containsKey(name) &&
			get(name).isJsonPrimitive() &&
			get(name).getAsJsonPrimitive().isString();
	}

	@Override
	public JsonElement putObject(String name, DataTable data) {
		return put(name, generateJsonObject(data));
	}

	@Override
	public JsonObject getObject(String name) {
		JsonElement element = getElement(name);

		assertTrue ("json " + name + " is not a object", element.isJsonObject());

		return element.getAsJsonObject();
	}

	@Override
	public JsonElement putArray(String name, DataTable template, DataTable data) {
		return put(name, generateJsonArray(template, data));
	}

	@Override
	public JsonElement putArray(String name, DataTable data) {
		return put(name, generateJsonArray(data));
	}

	@Override
	public JsonArray getArray(String name) {
		JsonElement element = getElement(name);

		assertTrue ("json " + name + " is not an aray", element.isJsonArray());

		return element.getAsJsonArray();
	}

	private JsonElement getElement (String name) {
		assertTrue ("json " + name + " does not exist", containsKey(name));
		return get(name);
	}

	// generate a json object base on data table
	@Override
	public JsonObject generateJsonObject (DataTable data) {
		JsonObject obj = new JsonObject ();

		for (var row : data.asLists()) {
			assertEquals("Json row must have exactly three elements", 3, row.size());
			obj.add(row.get(0), generateJsonElement(row.get(1), row.get(2)));
		}

		return obj;
	}

	@Override
	public boolean jsonObjectMatches(JsonObject object, DataTable data) {
		return jsonObjectMatches (object, generateJsonObject (data));
	}

	@Override
	public boolean jsonObjectMatches(JsonObject object, JsonObject data) {
		// verify the existence of all keys
		for (String key : data.keySet()) {
			if (!object.has(key)) {
				logger.info("object does not contain key " + key);
				return false;
			}

			// get the json elements to proceed with the rest of the tests
			JsonElement datElem = data.get(key);
			JsonElement objElem = object.get(key);

			if (objElem.isJsonPrimitive()) {
				// elements must match
				if (!objElem.equals(datElem)) {
					logger.info("object's " + key + " (" + objElem.toString() + ") does not match " + datElem.toString());
					return false;
				}

				// no need to check the rest of the cases
				continue;
			}

			if (objElem.isJsonNull()) {
				// data element must be null too
				if (!datElem.isJsonNull()) {
					logger.info("object's " + key + " is not a null");
					return false;
				}

				// no need to check the rest of the cases
				continue;
			}

			if (objElem.isJsonObject()) {
				// data element must be object too
				if (!datElem.isJsonObject()) {
					logger.info("object's " + key + " is not a object");
					return false;
				}

				// match objects recursively
				if (!jsonObjectMatches(objElem.getAsJsonObject(), datElem.getAsJsonObject())) {
					return false;
				}

				// no need to check the rest of the cases
				continue;
			}

			if (objElem.isJsonArray()) {
				// data must be an array too
				if (!datElem.isJsonArray()) {
					logger.info("object's " + key + " is not an array");
					return false;
				}

				// we need to match every item int he data list with the object list
				if (!jsonArrayMatches(objElem.getAsJsonArray(), datElem.getAsJsonArray())) {
					return false;
				}

				// no need to check the rest of the cases
				continue;
			}

			// no further types are implemented stop with an error if that ever happens
			assertNotNull ("object element could not be handled (unimplemented type)", null);
		}

		return true;
	}

	@Override
	public JsonArray generateJsonArray(DataTable template, DataTable data) {
		JsonArray arr = new JsonArray ();

		var maps = template.asMaps();
		assertEquals ("object template must have one row", 1, maps.size());
		var templateObject = maps.get(0);

		for (var row : data.asMaps()) {
			assertEquals ("object template and data table must be of the same size", templateObject.size(), row.size());
			JsonObject obj = new JsonObject();

			for (var key : row.keySet()) {
				assertTrue ("key " + key + " must be contained in template object", templateObject.containsKey(key));

				obj.add(key, generateJsonElement(templateObject.get(key), row.get(key)));
			}

			arr.add(obj);
		}

		return arr;
	}

	@Override
	public JsonArray generateJsonArray(DataTable data) {
		JsonArray arr = new JsonArray ();

		for (var row : data.asLists()) {
			assertEquals("Json array row must have exactly two elements", 2, row.size());
			arr.add(generateJsonElement(row.get(0), row.get(1)));
		}

		return arr;
	}

	@Override
	public boolean jsonArrayMatches (JsonArray array, JsonArray data) {
		// iterate the data array and match each item
		for (int i = 0; i < data.size(); i += 1) {
			JsonElement element = data.get(i);

			if (element.isJsonPrimitive() || element.isJsonNull()) {
				// elements must match
				if (!array.contains(element)) {
					logger.info("array does not contain " + element.toString());
					return false;
				}

				// no need to check the rest of the cases
				continue;
			}

			if (element.isJsonObject()) {
				// array must contain the object
				if (!jsonArrayContainsObject (array, element.getAsJsonObject())) {
					logger.info("array does not contain " + element.toString());
					return false;
				}

				// no need to check the rest of the cases
				continue;
			}

			if (element.isJsonArray()) {
				// array must contain the array
				if (!jsonArrayContainsArray (array, element.getAsJsonArray())) {
					logger.info("array does not contain " + element.toString());
					return false;
				}

				// no need to check the rest of the cases
				continue;
			}

			// no further types are implemented stop with an error if that ever happens
			assertNotNull ("array element could not be handled (unimplemented type)", null);
		}

		return true;
	}

	@Override
	public boolean jsonArrayContainsArray(JsonArray array, JsonArray data) {
		// iterate the data array and match each object
		for (int i = 0; i < array.size(); i += 1) {
			JsonElement element = array.get(i);

			if (element.isJsonArray() && jsonArrayMatches(element.getAsJsonArray(), data)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean jsonArrayContainsObject(JsonArray array, String key, String type, String value) {
		JsonObject obj = new JsonObject ();

		obj.add(key, generateJsonElement(type, value));

		return jsonArrayContainsObject (array, obj);
	}

	@Override
	public boolean jsonArrayContainsObject(JsonArray array, DataTable data) {
		return jsonArrayContainsObject (array, generateJsonObject(data));
	}

	@Override
	public boolean jsonArrayContainsObject(JsonArray array, JsonObject data) {
		// iterate the data array and match each object
		for (int i = 0; i < array.size(); i += 1) {
			JsonElement element = array.get(i);

			if (element.isJsonObject() && jsonObjectMatches(element.getAsJsonObject(), data)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean jsonArrayContainsObjectWithElement(JsonArray array, String key, JsonElement element) {
		JsonObject obj = new JsonObject();

		obj.add(key, element);

		return jsonArrayContainsObject(array, obj);
	}

	public JsonElement generateJsonElement (String type, String value) {

		if (value == null) {
			assertTrue(type + " elements can not have an empty value", type.equals("string") || type.equals("null"));
			value = "";
		}

		if (generators.containsKey(type)) {
			return generators.get(type).generate(value);
		}

		assertFalse ("Type " + type + " could not be converted to json value", true);
		return null;
	}

	@Override
	public String generateUrl(DataTable data) {
		String url = "";

		for (var row : data.asLists()) {
			assertEquals("Url row must have exactly two elements", 2, row.size());
			assertFalse("Cannot use a json to build a url", row.get(0).equals("json"));
			assertFalse("Cannot use a fraciton to build a url", row.get(0).equals("fraction"));

			url += "/" + generateJsonElement(row.get(0), row.get(1)).getAsString();
		}

		return url;
	}

	@Override
	public JsonElementGenerator getGenerator(String name) {
		return generators.get(name);
	}

	@Override
	public JsonElementGenerator putGenerator(String name, JsonElementGenerator generator) {
		if (generator == null)
			return generators.remove(name);
		return generators.put(name, generator);
	}

}




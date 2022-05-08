package com.aoede.commons.cucumber.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

@Service
public class JsonServiceImpl extends TestStorageServiceImpl<JsonElement> implements JsonService {
	private final static String STRING_REGEX = "\\{[^{}]*\\}";

	private Random random = new Random (System.currentTimeMillis());
	private Pattern stringPattern;

	private AbstractTestServiceDiscoveryService abstractTestServiceDiscoveryService;

	public JsonServiceImpl (
		AbstractTestServiceDiscoveryService abstractTestServiceDiscoveryService
	) {
		this.abstractTestServiceDiscoveryService = abstractTestServiceDiscoveryService;

		stringPattern = Pattern.compile(STRING_REGEX, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
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
	public JsonElement put(String name, DataTable data) {
		return put(name, generateJsonObject(data));
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
					logger.info("object's " + key + " does not match " + datElem.toString());
					return false;
				}

				// no need to check the rest of the cases
				continue;
			}

			if (objElem.isJsonNull()) {
				// data element must be null too
				if (!datElem.isJsonNull()) {
					logger.info("object's " + key + " is null");
					return false;
				}

				// no need to check the rest of the cases
				continue;
			}

			if (objElem.isJsonObject()) {
				// data element must be object too
				if (!datElem.isJsonObject()) {
					logger.info("object's " + key + " is an object");
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
					logger.info("object's " + key + " is an array");
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

			// no further types are implemented stop with an error if that ever happens
			assertNotNull ("array element could not be handled (unimplemented type)", null);
		}

		return true;
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

	/**
	 * Help functions to generate json
	 */

	private JsonElement generateJsonElement (String type, String value) {

		if (value == null) {
			assertTrue(type + " elements can not have an empty value", type.equals("string") || type.equals("null"));
			value = "";
		}

		switch (type) {
		case "int":
		case "integer":
			return new JsonPrimitive(Integer.parseInt(value));
		case "long":
			return new JsonPrimitive(Long.parseLong(value));
		case "number":
			return new JsonPrimitive(new BigInteger(value));
		case "string":
			return new JsonPrimitive(value);
		case "bool":
		case "boolean":
			return new JsonPrimitive(Boolean.parseBoolean(value));
		case "fraction":
			return buildFraction(value);
		case "compositeId":
			assertTrue("json value " + value + " is not a valid composite id", isCompositeKey(value));
			return get(value);
		case "key":
			AbstractTestService service = abstractTestServiceDiscoveryService.getService(value);
			assertNotNull("service for " + value + " was not found", service);

			JsonElement element = service.getLatestKey();
			assertNotNull("key for " + value + " was not found", element);

			return element;
		case "random string":
			return new JsonPrimitive(randomString(value));
		case "json":
			assertTrue (
				"json " + value + " was not found",
				containsKey(value)
			);
			return get(value);
		case "null":
			return JsonNull.INSTANCE;
		default:
			assertFalse ("Type " + type + " could not be converted to json value", true);
			return null;
		}

	}

	private String randomString (int length) {
		return random.ints(48, 123)
			.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
			.limit(length)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
	}

	private String randomString (String template) {
		var matcher = stringPattern.matcher(template);

		// find and process all matches
		while (matcher.find()) {
			String block = matcher.group();
			String[] parts = block.substring(1, block.length()-1).split(":");

			switch (parts[0]) {
				case "string":
					assertEquals("string template block must have two parts", 2, parts.length);
					int length = Integer.parseInt(parts[1]);
					assertTrue("string template block must define a positive number as the second part", length > 0);
					template = template.replace(block, randomString(length));
					break;
				default:
					assertNotNull("unknown template block for random string: " + parts[0], null);
			}
		}

		logger.info("generated random string : " + template);
		return template;
	}

	private JsonObject buildFraction (BigInteger num, BigInteger den) {
		JsonObject fraction = new JsonObject ();

		fraction.add( "numerator" , new JsonPrimitive(num));
		fraction.add("denominator", new JsonPrimitive(den));

		return fraction;
	}

	private JsonObject buildFraction (String fraction) {
		String[] parts = fraction.split("/");

		assertEquals("fraction must have two parts", 2, parts.length);

		return buildFraction (new BigInteger(parts[0].trim()), new BigInteger(parts[1].trim()));
	}

}




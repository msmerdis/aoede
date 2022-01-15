package com.aoede.commons.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

@Service
public class JsonObjectServiceImpl extends TestStorageServiceImpl<JsonObject> implements JsonObjectService {
	//private final static String STRING_REGEX = "([^{}]*(\\{[^{}]*\\}))*[^{}]*";
	private final static String STRING_REGEX = "\\{[^{}]*\\}";

	private Random random = new Random (System.currentTimeMillis());
	private Pattern stringPattern;

	@Autowired
	private AbstractTestServiceDiscoveryService abstractTestServiceDiscoveryService;

	@Lazy
	@Autowired
	private CompositeIdService compositeIdService;

	public JsonObjectServiceImpl () {
		stringPattern = Pattern.compile(STRING_REGEX, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	}

	@Override
	public void put(String name, DataTable data) {
		put(name, generateJson(data));
	}

	// generate a json object base on data table
	@Override
	public JsonObject generateJson (DataTable data) {
		JsonObject obj = new JsonObject ();

		for (var row : data.asLists()) {
			assertEquals("Json row must have exactly three elements", 3, row.size());
			switch (row.get(1)) {
			case "int":
			case "integer":
				obj.add(row.get(0), new JsonPrimitive(Integer.parseInt(row.get(2))));
			case "long":
				obj.add(row.get(0), new JsonPrimitive(Long.parseLong(row.get(2))));
				break;
			case "number":
				obj.add(row.get(0), new JsonPrimitive(new BigInteger(row.get(2))));
				break;
			case "string":
				obj.add(row.get(0), new JsonPrimitive(row.get(2)));
				break;
			case "bool":
			case "boolean":
				obj.add(row.get(0), new JsonPrimitive(Boolean.parseBoolean(row.get(2))));
				break;
			case "fraction":
				obj.add(row.get(0), buildFraction(row.get(2)));
				break;
			case "compositeId":
				assertTrue (
					"composite id " + row.get(1) + " was not found",
					compositeIdService.containsKey(row.get(1))
				);
				obj.add(row.get(0), new JsonPrimitive(compositeIdService.get(row.get(1))));
				break;
			case "key":
				JsonElement element = abstractTestServiceDiscoveryService.getService(row.get(2)).getLatestKey();
				assertNotNull("key for " + row.get(2) + " was not found", element);
				obj.add(row.get(0), element);
				break;
			case "random string":
				obj.add(row.get(0), new JsonPrimitive(randomString(row.get(2))));
				break;
			case "json":
				assertTrue (
					"json " + row.get(1) + " was not found",
					containsKey(row.get(2))
				);
				obj.add(row.get(0), get(row.get(2)));
				break;
			default:
				assertFalse ("Type " + row.get(1) + " could not be converted to json value", true);
			}
		}

		return obj;
	}

	@Override
	public boolean jsonObjectMatches(JsonObject object, DataTable data) {
		return jsonObjectMatches (object, generateJson (data));
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

				// match objects recursivly
				if (!jsonObjectMatches(objElem.getAsJsonObject(), datElem.getAsJsonObject())) {
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

	/**
	 * Help functions to generate json
	 */

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

		return buildFraction (new BigInteger(parts[0]), new BigInteger(parts[1]));
	}
}




package com.aoede.commons.service;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.http.HttpStatus;

import com.aoede.commons.base.ResponseResults;
import com.aoede.commons.base.component.BaseComponent;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.cucumber.datatable.DataTable;
import lombok.Getter;

@Getter
public abstract class AbstractTestService extends BaseComponent {
	protected Random random = new Random (System.currentTimeMillis());
	protected boolean success;
	protected String latestKey;
	protected String latestCreatedKey;
	protected JsonObject latestObj;
	protected JsonArray latestArr;
	protected ResponseResults latestResults;

	public abstract String getName ();
	public abstract String getPath ();

	public abstract String getKeyName ();

	final public String createBody() {
		JsonObject obj = new JsonObject();

		createDefaultBody (obj);

		return obj.toString();
	}

	final public String createBody (DataTable data) {
		JsonObject obj = new JsonObject();

		createBody (obj, data);

		return obj.toString();
	}

	final public String updateBody (DataTable data) {
		JsonObject obj = new JsonObject();

		updateBody (obj, data);

		return obj.toString();
	}

	abstract public void createDefaultBody (JsonObject obj);

	public void createBody (JsonObject obj, DataTable data) {
		for (var row : data.asLists()) {
			addJsonElement(obj, row.get(0), row.get(1));
		}
	}

	public void updateBody (JsonObject obj, DataTable data) {
		for (var row : data.asLists()) {
			addJsonElement(obj, row.get(0), row.get(1));
		}
	}

	protected abstract void addJsonElement (JsonObject obj, String name, String value);

	protected void results (ResponseResults results, int expectedStatus, boolean expectingBody, boolean multipleResults) {
		success =
			(results.status.value() == expectedStatus) &&
			((results.body.isEmpty()) != expectingBody);

		latestObj = null;
		latestArr = null;
		latestResults = results;

		JsonElement element = JsonParser.parseString(results.body);

		if (success && expectingBody && multipleResults) {
			assertTrue ("expecting json array as result", element.isJsonArray());
			latestArr = element.getAsJsonArray();
		}

		if (!success || (expectingBody && !multipleResults)) {
			assertTrue ("expecting json object as result", element.isJsonObject());
			latestObj = element.getAsJsonObject();
		}
	}

	public void searchResults (ResponseResults results) {
		results (results, HttpStatus.OK.value(), true, true);
	}

	public void accessResults(ResponseResults results) {
		results (results, HttpStatus.OK.value(), true, false);
	}

	public void findAllResults (ResponseResults results) {
		results (results, HttpStatus.OK.value(), true, true);
	}

	public void createResults (ResponseResults results) {
		results (results, HttpStatus.CREATED.value(), true, false);

		if (success && latestObj.has(getKeyName())) {
			latestKey = latestObj.get(getKeyName()).toString();
		}
	}

	public void updateResults (ResponseResults results) {
		results (results, HttpStatus.NO_CONTENT.value(), false, false);
	}

	public void deleteResults (ResponseResults results) {
		results (results, HttpStatus.NO_CONTENT.value(), false, false);
	}

	public void setup () {
		success = false;
	}

	public void cleanup () {
		latestKey = null;
		latestObj = null;
		latestArr = null;
		latestResults = null;
	}

	final public boolean lastKeyMatches (String key) {
		return latestKey.equals(key);
	}

	final public boolean containsKeyInElement (String element, String key, String value) {
		JsonElement keyElement = latestObj.get(element);

		if (!keyElement.isJsonArray()) {
			logger.error("element " + element + " is not an array");
			return false;
		}

		for (var item : keyElement.getAsJsonArray()) {
			if (!item.isJsonObject()) {
				logger.error("element " + element + " array does not contain objects");
				return false;
			}
			JsonObject obj = item.getAsJsonObject();

			if (!obj.has(key)) {
				logger.error("element " + element + " objects do not contain key " + key);
				return false;
			}
			if (obj.get(key).toString().equals(value))
				return true;
		}
		return false;
	}

	public boolean lastObjectMatches (DataTable data) {
		Map<String, String> element = new HashMap<String, String> ();

		for (var row : data.asLists()) {
			element.put(row.get(0), row.get(1));
		}

		return objectMatches (latestObj, element.entrySet());
	}

	public boolean lastArrayMatches(DataTable data) {
		for (var tableRow : data.asMaps()) {
			if (!arrayContains (latestArr, tableRow)) {
				return false;
			}
		}
		return true;
	}

	public boolean lastArrayContainsObjectWith(String id, String value) {
		for (var element : latestArr) {
			if (objectMatches(element.getAsJsonObject(), id, value))
				return true;
		}
		return false;
	}

	private boolean arrayContains (JsonArray arr, Map<String, String> result) {
		for (var element : arr) {
			JsonObject obj = element.getAsJsonObject();

			if (objectMatches (obj, result.entrySet())) {
				return true;
			}
		}

		return false;
	}

	protected boolean objectMatches (JsonObject obj, Set<Map.Entry<String, String>> values) {
		for (var value : values) {
			if (!objectMatches (obj, value.getKey(), value.getValue())) {
				return false;
			}
		}

		return true;
	}

	protected boolean objectMatches (JsonObject obj, String key, String value) {
		if (!obj.has(key)) {
			return false;
		}

		if (!obj.get(key).getAsString().equals(value)) {
			return false;
		}

		return true;
	}

	protected String randomString (int length) {
		return random.ints(48, 123)
			.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
			.limit(length)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
	}

}




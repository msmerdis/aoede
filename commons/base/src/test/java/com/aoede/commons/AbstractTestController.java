package com.aoede.commons;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.aoede.commons.base.component.BaseComponent;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

public abstract class AbstractTestController extends BaseComponent {
	protected boolean success;
	protected String latestKey;
	protected JsonObject latestObj;
	protected JsonArray latestArr;

	public abstract String getName ();
	public abstract String getPath ();

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

	public void createBody (JsonObject obj, DataTable data) {
		for (var row : data.asLists()) {
			obj.add(row.get(0), new JsonPrimitive(row.get(1)));
		}
	}

	public void updateBody (JsonObject obj, DataTable data) {
		for (var row : data.asLists()) {
			obj.add(row.get(0), new JsonPrimitive(row.get(1)));
		}
	}

	protected void results (HttpStatus responseStatus, int expectedStatus, String responseBody, boolean expectingBody, boolean multipleResults) {
		success =
			(responseStatus.value() == expectedStatus) &&
			((responseBody.isEmpty()) != expectingBody);

		if (!success || expectingBody) {
			if (success && multipleResults) {
				latestArr = JsonParser.parseString(responseBody).getAsJsonArray();
			} else {
				latestObj = JsonParser.parseString(responseBody).getAsJsonObject();
				if (success)
					latestKey = latestObj.get("id").getAsString();
			}
		}
	}

	public void searchResults (HttpStatus responseStatus, HttpHeaders responseHeaders, String responseBody) {
		results (responseStatus, HttpStatus.OK.value(), responseBody, true, true);
	}

	public void accessResults(HttpStatus responseStatus, HttpHeaders responseHeaders, String responseBody) {
		results (responseStatus, HttpStatus.OK.value(), responseBody, true, false);
	}

	public void findAllResults (HttpStatus responseStatus, HttpHeaders responseHeaders, String responseBody) {
		results (responseStatus, HttpStatus.OK.value(), responseBody, true, true);
	}

	public void createResults (HttpStatus responseStatus, HttpHeaders responseHeaders, String responseBody) {
		results (responseStatus, HttpStatus.CREATED.value(), responseBody, true, false);
	}

	public void updateResults (HttpStatus responseStatus, HttpHeaders responseHeaders, String responseBody) {
		results (responseStatus, HttpStatus.NO_CONTENT.value(), responseBody, false, false);
	}

	public void deleteResults (HttpStatus responseStatus, HttpHeaders responseHeaders, String responseBody) {
		results (responseStatus, HttpStatus.NO_CONTENT.value(), responseBody, false, false);
	}

	public void setup () {
		success = false;
	}

	public void cleanup () {
		latestKey = null;
		latestObj = null;
		latestArr = null;
	}

	final public boolean isSuccessful () {
		return success;
	}

	final public boolean lastKeyMatches (String key) {
		return latestKey.equals(key);
	}

	final public boolean lastObjectMatches (DataTable data) {
		Map<String, String> element = new HashMap<String, String> ();

		for (var row : data.asLists()) {
			element.put(row.get(0), row.get(1));
		}

		return objectMatches (latestObj, element.entrySet());
	}

	final public boolean lastArrayMatches(DataTable data) {
		for (var tableRow : data.asMaps()) {
			if (!arrayContains (latestArr, tableRow)) {
				return false;
			}
		}
		return true;
	}

	final public boolean lastArrayContainsObjectWith(String id, String value) {
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

}




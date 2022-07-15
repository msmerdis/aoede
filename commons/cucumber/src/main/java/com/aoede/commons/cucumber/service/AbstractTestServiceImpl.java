package com.aoede.commons.cucumber.service;

import static org.junit.Assert.assertTrue;

import org.springframework.http.HttpStatus;

import com.aoede.commons.cucumber.BaseTestComponent;
import com.aoede.commons.cucumber.ResponseResults;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Getter;

@Getter
public abstract class AbstractTestServiceImpl extends BaseTestComponent implements AbstractTestService {
	private boolean success;
	private JsonElement latestKey;
	private JsonObject latestObj;
	private JsonArray latestArr;
	private ResponseResults latestResults;

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

	@Override
	public void searchResults (ResponseResults results) {
		results (results, HttpStatus.OK.value(), true, true);
	}

	@Override
	public void accessResults(ResponseResults results) {
		results (results, HttpStatus.OK.value(), true, false);
	}

	@Override
	public void findAllResults (ResponseResults results) {
		results (results, HttpStatus.OK.value(), true, true);
	}

	@Override
	public void createResults (ResponseResults results) {
		results (results, HttpStatus.CREATED.value(), true, false);

		if (success && latestObj.has(getKeyName())) {
			latestKey = latestObj.get(getKeyName());
		}
	}

	@Override
	public void updateResults (ResponseResults results) {
		results (results, HttpStatus.NO_CONTENT.value(), false, false);
	}

	@Override
	public void deleteResults (ResponseResults results) {
		results (results, HttpStatus.NO_CONTENT.value(), false, false);
	}

	@Override
	public void setup () {
		success = false;
	}

	@Override
	public void clear () {
		success = false;
		latestKey = null;
		latestObj = null;
		latestArr = null;
		latestResults = null;
	}

}




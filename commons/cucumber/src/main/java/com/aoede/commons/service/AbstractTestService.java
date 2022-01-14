package com.aoede.commons.service;

import com.aoede.commons.base.ResponseResults;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.cucumber.datatable.DataTable;

public interface AbstractTestService {
	public String getName ();
	public String getPath ();

	public String getKeyName ();

	public void setup();
	public void cleanup();

	public void findAllResults(ResponseResults results);

	public void searchResults(ResponseResults results);
	public void createResults(ResponseResults results);
	public void accessResults(ResponseResults results);
	public void updateResults(ResponseResults results);
	public void deleteResults(ResponseResults results);

	public boolean isSuccess();

	public JsonElement getLatestKey();
	public ResponseResults getLatestResults();

	public String createBody();
	public String createBody(DataTable data);

	public boolean lastArrayMatches(DataTable data);
	public boolean lastArrayContainsObjectWith(String id, String value);
	public boolean lastObjectMatches(DataTable data);
	public boolean containsKeyInElement(String element, String keyName, String latestKey);

	public JsonArray getLatestArr();
	public JsonObject getLatestObj();
}




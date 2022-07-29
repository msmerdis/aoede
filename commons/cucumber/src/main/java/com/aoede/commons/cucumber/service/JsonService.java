package com.aoede.commons.cucumber.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.cucumber.datatable.DataTable;

public interface JsonService extends TestStorageService<JsonElement> {

	// composite key

	public String generateBase64(String data);
	public String generatePaddedBase64(String data);
	public String generateCompositeKey(DataTable data);

	public String putCompositeKey(String name, DataTable data);
	public String getCompositeKey(String name);

	// json object

	public JsonElement generateJsonElement (String type, String value);
	public JsonObject  generateJsonObject  (DataTable data);

	public JsonElement putObject (String name, DataTable data);
	public JsonObject  getObject (String name);

	public boolean jsonObjectMatches(JsonObject object, DataTable  data);
	public boolean jsonObjectMatches(JsonObject object, JsonObject data);

	// json array

	public JsonArray generateJsonArray(DataTable template, DataTable data);
	public JsonArray generateJsonArray(DataTable data);

	public JsonElement putArray (String name, DataTable template, DataTable data);
	public JsonElement putArray (String name, DataTable data);
	public JsonArray   getArray (String name);

	public boolean jsonArrayMatches(JsonArray object, JsonArray data);

	public boolean jsonArrayContainsObject(JsonArray array, String key, String type, String value);
	public boolean jsonArrayContainsObject(JsonArray array, DataTable  data);
	public boolean jsonArrayContainsObject(JsonArray array, JsonObject data);

	public boolean jsonArrayContainsArray(JsonArray object, JsonArray data);

	public boolean jsonArrayContainsObjectWithElement(JsonArray array, String key, JsonElement element);

	// url

	public String generateUrl(DataTable data);
}




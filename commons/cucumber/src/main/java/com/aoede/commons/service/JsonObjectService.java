package com.aoede.commons.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.cucumber.datatable.DataTable;

public interface JsonObjectService extends TestStorageService<JsonObject> {
	public JsonObject generateJson(DataTable data);

	public boolean jsonObjectMatches(JsonObject object, DataTable  data);
	public boolean jsonObjectMatches(JsonObject object, JsonObject data);

	public JsonArray generateJsonArray(DataTable template, DataTable data);

	public boolean jsonArrayMatches(JsonArray object, JsonArray data);

	public boolean jsonArrayContainsObject(JsonArray array, String key, String type, String value);
	public boolean jsonArrayContainsObject(JsonArray array, DataTable  data);
	public boolean jsonArrayContainsObject(JsonArray array, JsonObject data);

	public boolean jsonArrayContainsObjectWithElement(JsonArray array, String key, JsonElement element);
}




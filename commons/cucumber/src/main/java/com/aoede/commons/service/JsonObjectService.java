package com.aoede.commons.service;

import com.google.gson.JsonObject;

import io.cucumber.datatable.DataTable;

public interface JsonObjectService extends TestStorageService<JsonObject> {
	public JsonObject generateJson(DataTable data);

	public boolean jsonObjectMatches(JsonObject object, DataTable  data);
	public boolean jsonObjectMatches(JsonObject object, JsonObject data);
}




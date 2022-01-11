package com.aoede.commons.service;

import com.google.gson.JsonObject;

import io.cucumber.datatable.DataTable;

public interface JsonObjectService extends TestStorageService<JsonObject> {
	JsonObject generateJson(DataTable data);
}




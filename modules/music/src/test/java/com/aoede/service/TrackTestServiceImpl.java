package com.aoede.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestServiceImpl;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

@Component
public class TrackTestServiceImpl extends AbstractTestServiceImpl implements TrackTestService {
	@Autowired
	SheetTestService sheetTestService;

	@Override
	public String getName() {
		return "track";
	}

	@Override
	public String getPath() {
		return "/api/track";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

	@Override
	public void createBody (JsonObject obj, DataTable data) {
		JsonElement key = sheetTestService.getLatestKey();

		if (key != null) obj.add("sheetId", key);

		super.createBody(obj, data);
	}

	@Override
	protected void addJsonElement (JsonObject obj, String name, String value) {
		switch (name) {
		default:
			obj.add(name, new JsonPrimitive(value));
		}
	}

	@Override
	protected boolean objectMatches (JsonObject obj, String key, String value) {
		switch (key) {
			case "clef":
				return super.objectMatches(obj.get("clef").getAsJsonObject(), "id", value);
			default:
				return super.objectMatches(obj, key, value);
		}
	}
}




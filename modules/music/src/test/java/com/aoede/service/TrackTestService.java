package com.aoede.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestService;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

@Component
public class TrackTestService extends AbstractTestService {

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
		return "trackId";
	}

	@Override
	public void createBody (JsonObject obj, DataTable data) {
		obj.add("sheetId", new JsonPrimitive(Integer.parseInt(sheetTestService.getLatestKey())));

		super.createBody(obj, data);
	}

	@Override
	public void createDefaultBody(JsonObject obj) {
		obj.add("clef", new JsonPrimitive("Treble"));
	}

	@Override
	protected JsonPrimitive getPrimitive(String name, String value) {
		switch (name) {
			case "id":
				return new JsonPrimitive(Long.parseLong(value));
			default:
				return new JsonPrimitive(value);
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




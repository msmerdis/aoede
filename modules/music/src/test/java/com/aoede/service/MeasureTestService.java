package com.aoede.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestService;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

@Component
public class MeasureTestService extends AbstractTestService {
	@Autowired
	SectionTestService sectionTestService;

	@Override
	public String getName() {
		return "measure";
	}

	@Override
	public String getPath() {
		return "/api/measure";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

	@Override
	public void createBody (JsonObject obj, DataTable data) {
		obj.add("sectionId", new JsonPrimitive(sectionTestService.getLatestKey()));

		super.createBody(obj, data);
	}

	@Override
	public void createDefaultBody(JsonObject obj) {
		obj.add("sectionId", new JsonPrimitive(sectionTestService.getLatestKey()));
	}

	@Override
	protected void addJsonElement (JsonObject obj, String name, String value) {
		switch (name) {
			case "id":
				obj.add(name, new JsonPrimitive(Long.parseLong(value)));
				break;
			default:
				obj.add(name, new JsonPrimitive(value));
		}
	}

}




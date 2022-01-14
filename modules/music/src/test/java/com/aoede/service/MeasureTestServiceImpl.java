package com.aoede.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestServiceImpl;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

@Component
public class MeasureTestServiceImpl extends AbstractTestServiceImpl implements MeasureTestService {
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
		JsonElement key = sectionTestService.getLatestKey();

		if (key != null) obj.add("sectionId", key);

		super.createBody(obj, data);
	}

	@Override
	public void createDefaultBody(JsonObject obj) {
		obj.add("sectionId", sectionTestService.getLatestKey());
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




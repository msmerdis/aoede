package com.aoede.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestServiceImpl;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

@Component
public class SectionTestServiceImpl extends AbstractTestServiceImpl implements SectionTestService {
	@Autowired
	TrackTestService trackTestService;

	@Override
	public String getName() {
		return "section";
	}

	@Override
	public String getPath() {
		return "/api/section";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

	@Override
	public void createBody (JsonObject obj, DataTable data) {
		JsonElement key = trackTestService.getLatestKey();

		if (key != null) obj.add("trackId", key);

		super.createBody(obj, data);
	}

	@Override
	protected void addJsonElement (JsonObject obj, String name, String value) {
		switch (name) {
			case "timeSignature":
				JsonObject inner = new JsonObject();
				String[] parts = value.split("/");

				inner.add( "numerator" , new JsonPrimitive(Integer.parseInt(parts[0])));
				inner.add("denominator", new JsonPrimitive(Integer.parseInt(parts[1])));

				obj.add(name, inner);
				break;
			default:
				obj.add(name, new JsonPrimitive(value));
		}
	}

	@Override
	protected boolean objectMatches (JsonObject obj, String key, String value) {
		switch (key) {
			case "timeSignature":
				JsonElement inner = obj.get(key);

				if (!inner.isJsonObject())
					return false;

				obj = inner.getAsJsonObject();

				String signature =
					obj.get( "numerator" ).getAsString() + "/" +
					obj.get("denominator").getAsString();

				return signature.equals(value);
			default:
				return super.objectMatches(obj, key, value);
		}
	}

}




package com.aoede.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

@Component
public class NoteTestService extends AbstractTestService {
	@Autowired
	MeasureTestService measureTestService;

	@Override
	public String getName() {
		return "note";
	}

	@Override
	public String getPath() {
		return "/api/note";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

	@Override
	public void createBody (JsonObject obj, DataTable data) {
		obj.add("measureId", new JsonPrimitive(measureTestService.getLatestKey()));

		super.createBody(obj, data);
	}

	@Override
	public void createDefaultBody(JsonObject obj) {
		JsonObject fraction = new JsonObject ();

		fraction.add( "numerator" , new JsonPrimitive(4));
		fraction.add("denominator", new JsonPrimitive(4));

		obj.add("measureId", new JsonPrimitive(measureTestService.getLatestKey()));
		obj.add("note", new JsonPrimitive(random.nextInt(60) + 40));
		obj.add("value", fraction);
	}

	@Override
	protected void addJsonElement (JsonObject obj, String name, String value) {
		switch (name) {
			case "value":
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
			case "value":
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




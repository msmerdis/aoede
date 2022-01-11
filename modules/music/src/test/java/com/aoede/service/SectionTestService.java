package com.aoede.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

@Component
public class SectionTestService extends AbstractTestService {
	private static final long serialVersionUID = 1L;

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
		obj.add("trackId", new JsonPrimitive(trackTestService.getLatestKey()));

		super.createBody(obj, data);
	}

	@Override
	public void createDefaultBody(JsonObject obj) {
		JsonObject fraction = new JsonObject ();

		fraction.add( "numerator" , new JsonPrimitive(4));
		fraction.add("denominator", new JsonPrimitive(4));

		obj.add("trackId", new JsonPrimitive(trackTestService.getLatestKey()));
		obj.add("tempo", new JsonPrimitive(random.nextInt(200) + 40));
		obj.add("keySignature", new JsonPrimitive(randomString(12)));
		obj.add("timeSignature", fraction);

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




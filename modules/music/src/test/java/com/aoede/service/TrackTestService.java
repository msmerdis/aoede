package com.aoede.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestService;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

@Component
public class TrackTestService extends AbstractTestService {

	@Override
	public String getName() {
		return "track";
	}

	@Override
	public String getPath() {
		return "/api/track";
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




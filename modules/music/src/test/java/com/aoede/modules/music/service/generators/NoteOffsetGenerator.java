package com.aoede.modules.music.service.generators;

import com.aoede.commons.cucumber.service.JsonElementGenerator;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class NoteOffsetGenerator implements JsonElementGenerator {
	@Override
	public JsonElement generate(String value) {
		JsonObject object = new JsonObject();

		if (value.endsWith("+")) {
			object.add("accidental", new JsonPrimitive(1));
		}

		if (value.endsWith("-")) {
			object.add("accidental", new JsonPrimitive(-1));
		}

		if (object.has("accidental") == false) {
			object.add("accidental", new JsonPrimitive(0));
		} else {
			value = value.substring(0, value.length() - 1);
		}

		object.add("offset", new JsonPrimitive(Integer.parseInt(value)));

		return object;
	}
}




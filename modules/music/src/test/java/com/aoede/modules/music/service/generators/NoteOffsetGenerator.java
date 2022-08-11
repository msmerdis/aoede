package com.aoede.modules.music.service.generators;

import com.aoede.commons.cucumber.service.JsonElementGenerator;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class NoteOffsetGenerator implements JsonElementGenerator {
	@Override
	public JsonElement generate(String value) {
		JsonObject object = new JsonObject();

		int accidental = 0;
		int offset     = 0;

		if (value.endsWith("+")) {
			accidental = 1;
		}

		if (value.endsWith("-")) {
			accidental = -1;
		}

		if (accidental == 0) {
			offset = Integer.parseInt(value);
		} else {
			offset = Integer.parseInt(value.substring(0, value.length() - 1));
		}

		object.add("offset",     new JsonPrimitive(offset));
		object.add("accidental", new JsonPrimitive(accidental));

		return object;
	}
}




package com.aoede.modules.music.service.generators;

import com.aoede.commons.cucumber.service.JsonElementGenerator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class NoteOffsetArrayGenerator implements JsonElementGenerator {

	private NoteOffsetGenerator noteOffsetGenerator;

	public NoteOffsetArrayGenerator (
		NoteOffsetGenerator noteOffsetGenerator
	) {
		this.noteOffsetGenerator = noteOffsetGenerator;
	}

	@Override
	public JsonElement generate(String value) {
		String[] offsets = value.split(" +");
		JsonArray array = new JsonArray();

		for(String offset : offsets) {
			array.add(noteOffsetGenerator.generate(offset));
		}

		return array;
	}
}




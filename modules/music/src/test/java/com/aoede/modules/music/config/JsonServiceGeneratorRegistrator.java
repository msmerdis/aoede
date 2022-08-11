package com.aoede.modules.music.config;

import org.springframework.context.annotation.Configuration;

import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.modules.music.service.generators.NoteOffsetArrayGenerator;
import com.aoede.modules.music.service.generators.NoteOffsetGenerator;

@Configuration
public class JsonServiceGeneratorRegistrator {

	public JsonServiceGeneratorRegistrator (JsonService service) {
		NoteOffsetGenerator generator = new NoteOffsetGenerator();

		service.putGenerator("note offset", generator);
		service.putGenerator("note offset array", new NoteOffsetArrayGenerator(generator));
	}

}




package com.aoede.modules.music.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.aoede.commons.base.converter.CompositeKeyDecoderFactory;
import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.modules.music.service.generators.NoteOffsetArrayGenerator;
import com.aoede.modules.music.service.generators.NoteOffsetGenerator;

@Configuration
public class TestConfig implements WebMvcConfigurer {

	public TestConfig (JsonService service) {
		registerGenerators (service);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(new CompositeKeyDecoderFactory());
	}

	@Bean
	public BCryptPasswordEncoder musicPasswordEncoder () {
		return new BCryptPasswordEncoder(12);
	}

	private void registerGenerators (JsonService service) {
		NoteOffsetGenerator generator = new NoteOffsetGenerator();

		service.putGenerator("note offset", generator);
		service.putGenerator("note offset array", new NoteOffsetArrayGenerator(generator));
	}

}




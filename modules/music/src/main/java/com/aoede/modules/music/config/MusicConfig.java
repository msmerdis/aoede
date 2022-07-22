package com.aoede.modules.music.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.aoede.modules.music.converter.FractionDecoderFactory;

@Configuration
public class MusicConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(new FractionDecoderFactory());
	}

}




package com.aoede.commons.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.aoede.commons.base.converter.CompositeKeyDecoderFactory;

@Configuration
public class TestConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(new CompositeKeyDecoderFactory());
	}

}




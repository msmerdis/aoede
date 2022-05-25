package com.aoede.modules.music.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.aoede.commons.base.converter.CompositeKeyDecoderFactory;

@Configuration
public class TestConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(new CompositeKeyDecoderFactory());
	}

	@Bean
	public BCryptPasswordEncoder musicPasswordEncoder () {
		return new BCryptPasswordEncoder(12);
	}

}




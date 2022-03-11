package com.aoede.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.aoede.commons.base.component.BaseComponent;

@Configuration
@EnableWebMvc
public class StaticResourceConfiguration extends BaseComponent implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/app/**")
			.addResourceLocations("classpath:/static/");

		registry
			.addResourceHandler("/static/**")
			.addResourceLocations("classpath:/static/");
	}

}




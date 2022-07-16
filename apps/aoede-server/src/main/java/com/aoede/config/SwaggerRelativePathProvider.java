package com.aoede.config;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import springfox.documentation.spring.web.paths.RelativePathProvider;

@Component
public class SwaggerRelativePathProvider extends RelativePathProvider {

	public SwaggerRelativePathProvider(ServletContext servletContext) {
		super(servletContext);
	}

	@Override
	public String getApplicationBasePath() {
		return "/";
	}

}




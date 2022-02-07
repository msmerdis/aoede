package com.aoede.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends WebMvcConfigurationSupport {

	SwaggerRelativePathProvider swaggerRelativePathProvider;

	public SwaggerConfiguration(SwaggerRelativePathProvider swaggerRelativePathProvider) {
		this.swaggerRelativePathProvider = swaggerRelativePathProvider;
	}

	@Value("${server.port}")
	private int serverPort;

	@Bean
	public Docket api() {
		String hostname = "localhost";

		if (serverPort != 80) {
			hostname += ":" + serverPort;
		}

		return new Docket(DocumentationType.SWAGGER_2)
			.host(hostname)
			.pathProvider(swaggerRelativePathProvider)
			.useDefaultResponseMessages(true)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.ant("/**"))
			.build();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}




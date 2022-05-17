package com.aoede.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.modules.user.config.UserConfiguration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends BaseComponent implements WebMvcConfigurer {

	private ApiInfo apiInfo (String title, String description) {
		return new ApiInfoBuilder()
			.version("1.0")
			.title(title)
			.description(description)
			.license("Apache 2.0")
			.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
			.termsOfServiceUrl("/static/tos.html")
			.build();
	}

	private Parameter authTokenParameter() {
		return new ParameterBuilder()
			.name(UserConfiguration.TOKEN_HEADER_NAME)
			.allowEmptyValue(Boolean.FALSE)
			.description("User Authentication Token")
			.parameterType("header")
			.modelRef(new ModelRef("string"))
			.required(true)
			.build();
	}

	@Bean
	public Docket apiSwagger() {
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName("api-docs")
			.useDefaultResponseMessages(true)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.ant("/api/**"))
			.build()
			.apiInfo(apiInfo("Aoede User API", "Operations to manage music sheets"))
			.globalOperationParameters(List.of(authTokenParameter()));
	}

	@Bean
	public Docket adminSwagger() {
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName("admin-docs")
			.useDefaultResponseMessages(true)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.ant("/admin/**"))
			.build()
			.apiInfo(apiInfo("Aoede Admin API", "Operations to manage users and roles"))
			.globalOperationParameters(List.of(authTokenParameter()));
	}

	@Bean
	public Docket authSwagger() {
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName("auth-docs")
			.useDefaultResponseMessages(true)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.ant("/login"))
			.build()
			.apiInfo(apiInfo("Aoede Authentication API", "Operations to login and refresh authentication token"));
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}




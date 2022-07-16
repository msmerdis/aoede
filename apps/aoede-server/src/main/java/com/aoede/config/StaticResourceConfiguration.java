package com.aoede.config;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import com.aoede.commons.base.component.BaseComponent;

@Configuration
@EnableWebMvc
public class StaticResourceConfiguration extends BaseComponent implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/app/**", "/app/", "/app")
			.addResourceLocations("classpath:/static/")
			.resourceChain(false)
			.addResolver(new PathResourceResolver() {
				@Override
				protected Resource resolveResourceInternal(@Nullable HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {
					Resource resource = super.resolveResourceInternal(request, requestPath, locations, chain);
					return Objects.isNull(resource) ? super.resolveResourceInternal(request, "index.html", locations, chain) : resource;
				}
			});

		registry
			.addResourceHandler("/static/**")
			.addResourceLocations("classpath:/static/");
	}

}




package com.aoede.modules.user.service;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.aoede.commons.cucumber.RequestParametersCallback;
import com.aoede.commons.cucumber.ResponseResults;
import com.aoede.modules.user.config.UserConfiguration;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Getter;

@Getter
@Component
public class LoginTestServiceImpl implements LoginTestService {
	private boolean success;
	private String loginToken;
	private ResponseResults latestResults;
	private JsonObject userDetails;

	@PostConstruct
	public void registerAddon () {
		RequestParametersCallback.register(this);
	}

	@Override
	public void results (ResponseResults results) {
		JsonElement element = JsonParser.parseString(results.body);

		success =
			results.status.value() == HttpStatus.OK.value() &&
			results.headers.containsKey(UserConfiguration.TOKEN_HEADER_NAME) &&
			element.isJsonObject();
		latestResults = results;

		if (success)
			loginToken = results.headers.get(UserConfiguration.TOKEN_HEADER_NAME).get(0);

		if (element.isJsonObject())
			userDetails = element.getAsJsonObject();
	}

	@Override
	public void configure(HttpHeaders headers) {
		if (success) {
			headers.add(UserConfiguration.TOKEN_HEADER_NAME, loginToken);
		}
	}

}




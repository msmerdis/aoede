package com.aoede.modules.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

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

	@Override
	public void results (ResponseResults results) {
		JsonElement element = JsonParser.parseString(results.body);

		success =
			results.status.value() == HttpStatus.ACCEPTED.value() &&
			results.headers.containsKey(UserConfiguration.TOKEN_HEADER_NAME) &&
			element.isJsonObject();
		latestResults = results;

		if (success)
			loginToken = results.headers.get(UserConfiguration.TOKEN_HEADER_NAME).get(0);

		if (element.isJsonObject())
			userDetails = element.getAsJsonObject();
	}

}




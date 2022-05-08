package com.aoede.modules.user.service;

import static org.junit.Assert.assertTrue;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.aoede.commons.cucumber.RequestParametersCallback;
import com.aoede.commons.cucumber.ResponseResults;
import com.aoede.commons.cucumber.service.AbstractTestServiceImpl;
import com.aoede.modules.user.config.UserConfiguration;

import lombok.Getter;

@Getter
@Component
public class LoginTestServiceImpl extends AbstractTestServiceImpl implements LoginTestService {
	private String loginToken;

	@PostConstruct
	public void registerAddon () {
		RequestParametersCallback.register(this);
	}

	@Override
	public void configure(HttpHeaders headers) {
		if (isSuccess()) {
			headers.add(UserConfiguration.TOKEN_HEADER_NAME, loginToken);
		}
	}

	@Override
	public String getName() {
		return "login";
	}

	@Override
	public String getPath() {
		return "/login";
	}

	@Override
	public String getKeyName() {
		return "username";
	}

	@Override
	public void loginResults (ResponseResults results) {
		if (results.headers.containsKey(UserConfiguration.TOKEN_HEADER_NAME)) {
			super.results(results, 200, true, false);
		} else {
			super.results(results, -1, true, false);
		}

		if (isSuccess()) {
			loginToken = results.headers.get(UserConfiguration.TOKEN_HEADER_NAME).get(0);
		}
	}

	@Override
	protected void results (ResponseResults results, int expectedStatus, boolean expectingBody, boolean multipleResults) {
		assertTrue("login service does not support this operation", false);
	}
}




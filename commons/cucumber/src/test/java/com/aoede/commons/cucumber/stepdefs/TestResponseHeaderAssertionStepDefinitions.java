package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.http.HttpHeaders;

import com.aoede.ResponseHeaderAssertionStepDefinitionsTestCaseSetup;
import com.aoede.commons.cucumber.ResponseResults;

public class TestResponseHeaderAssertionStepDefinitions extends ResponseHeaderAssertionStepDefinitionsTestCaseSetup {

	private void verifyHeaderSetup (String domain, String token) {
		var service = createLatestServiceMock();
		var results = new ResponseResults();

		results.headers = new HttpHeaders();
		if (token != null)
			results.headers.add("X-User-Token", token);

		when(service.getLatestResults()).thenReturn(results);

		if (domain != null)
			when(services.getService(eq("domain"))).thenReturn(service);
		else
			when(services.getLatestService()).thenReturn(service);
	}

	@Test
	public void verifyHeaderSuccess () throws Exception {
		verifyHeaderSetup(null, "the token");

		uut().verifyHeader("X-User-Token");
	}

	@Test
	public void verifyHeaderFailure () throws Exception {
		verifyHeaderSetup(null, null);

		assertThrows ("token was not supposed to be found", AssertionError.class, () -> {
			uut().verifyHeader("X-User-Token");
		});
	}

	@Test
	public void verifyNamedHeaderSuccess () throws Exception {
		verifyHeaderSetup("domain", "the token");

		uut().verifyHeader("domain", "X-User-Token");
	}

	@Test
	public void verifyNamedHeaderFailure () throws Exception {
		verifyHeaderSetup("domain", null);

		assertThrows ("token was not supposed to be found", AssertionError.class, () -> {
			uut().verifyHeader("domain", "X-User-Token");
		});
	}

	@Test
	public void matchHeaderSuccess () throws Exception {
		verifyHeaderSetup(null, "the token");

		uut().matchHeader("X-User-Token", "the token");
	}

	@Test
	public void matchHeaderFailure () throws Exception {
		verifyHeaderSetup(null, null);

		assertThrows ("token was not supposed to be found", AssertionError.class, () -> {
			uut().matchHeader("X-User-Token", "the token");
		});
	}

	@Test
	public void matchNamedHeaderSuccess () throws Exception {
		verifyHeaderSetup("domain", "the token");

		uut().matchHeader("domain", "X-User-Token", "the token");
	}

	@Test
	public void matchNamedHeaderFailure () throws Exception {
		verifyHeaderSetup("domain", null);

		assertThrows ("token was not supposed to be found", AssertionError.class, () -> {
			uut().matchHeader("domain", "X-User-Token", "the token");
		});
	}

}




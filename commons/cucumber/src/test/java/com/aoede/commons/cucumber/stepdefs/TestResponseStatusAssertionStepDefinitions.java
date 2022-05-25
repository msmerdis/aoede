package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.aoede.ResponseStatusAssertionStepDefinitionsTestCaseSetup;
import com.aoede.commons.cucumber.ResponseResults;

public class TestResponseStatusAssertionStepDefinitions extends ResponseStatusAssertionStepDefinitionsTestCaseSetup {

	private void verifySuccessfulRequest (boolean status) throws Exception {
		var uut = uut ();

		when(services.getLatestService()).thenReturn(latestService);
		when(latestService.isSuccess()).thenReturn(status);

		uut.verifySuccessfulRequest();
	}

	@Test
	public void verifySuccessfulRequestSuccess () throws Exception {
		verifySuccessfulRequest(true);
		verify(latestService).isSuccess();
	}

	@Test
	public void verifySuccessfulRequestFailure () throws Exception {
		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifySuccessfulRequest(false);
		});
	}

	private void verifyNamedSuccessfulRequest (String domain, boolean status) throws Exception {
		var uut = uut ();

		when(services.getService(eq(domain))).thenReturn(latestService);
		when(latestService.isSuccess()).thenReturn(status);

		uut.verifySuccessfulRequest(domain);
	}

	@Test
	public void verifyNamedSuccessfulRequestSuccess () throws Exception {
		verifyNamedSuccessfulRequest("test", true);
		verify(latestService).isSuccess();
	}

	@Test
	public void verifyNamedSuccessfulRequestFailure () throws Exception {
		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyNamedSuccessfulRequest("test", false);
		});
	}

	private void verifyNotSuccessfulRequest (boolean status) throws Exception {
		var uut = uut ();

		when(services.getLatestService()).thenReturn(latestService);
		when(latestService.isSuccess()).thenReturn(status);

		uut.verifyNotSuccessfulRequest();
	}

	@Test
	public void verifyNotSuccessfulRequestSuccess () throws Exception {
		verifyNotSuccessfulRequest(false);
		verify(latestService).isSuccess();
	}

	@Test
	public void verifyNotSuccessfulRequestFailure () throws Exception {
		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyNotSuccessfulRequest(true);
		});
	}

	private void verifyNamedNotSuccessfulRequest (String domain, boolean status) throws Exception {
		var uut = uut ();

		when(services.getService(eq(domain))).thenReturn(latestService);
		when(latestService.isSuccess()).thenReturn(status);

		uut.verifyNotSuccessfulRequest(domain);
	}

	@Test
	public void verifyNamedNotSuccessfulRequestSuccess () throws Exception {
		verifyNamedNotSuccessfulRequest("fail", false);
		verify(latestService).isSuccess();
	}

	@Test
	public void verifyNamedNotSuccessfulRequestFailure () throws Exception {
		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyNamedNotSuccessfulRequest("fail", true);
		});
	}

	private void verifyStatus (int status) throws Exception {
		var uut = uut ();
		var results = new ResponseResults();
		results.status = HttpStatus.OK;

		when(services.getLatestService()).thenReturn(latestService);
		when(latestService.getLatestResults()).thenReturn(results);

		uut.verifyStatus(status);
	}

	@Test
	public void verifyStatusSuccess () throws Exception {
		verifyStatus(200);
		verify(latestService).getLatestResults();
	}

	@Test
	public void verifyStatusFailure () throws Exception {
		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyStatus(201);
		});
	}

	private void verifyNamedStatus (String domain, int status) throws Exception {
		var uut = uut ();
		var results = new ResponseResults();
		results.status = HttpStatus.OK;

		when(services.getService(eq(domain))).thenReturn(latestService);
		when(latestService.getLatestResults()).thenReturn(results);

		uut.verifyStatus(domain, status);
	}

	@Test
	public void verifyNamedStatusSuccess () throws Exception {
		verifyNamedStatus("Status", 200);
		verify(latestService).getLatestResults();
	}

	@Test
	public void verifyNamedStatusFailure () throws Exception {
		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyNamedStatus("Status", 201);
		});
	}

}




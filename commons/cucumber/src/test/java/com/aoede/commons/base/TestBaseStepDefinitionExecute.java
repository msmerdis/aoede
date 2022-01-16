package com.aoede.commons.base;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.aoede.BaseStepDefinitionTestCaseSetup;

public class TestBaseStepDefinitionExecute extends BaseStepDefinitionTestCaseSetup {

	@Test
	public void verifyRawGetRequest() throws Exception {
		BaseStepDefinition uut = uut ();

		ResponseResults results = new ResponseResults ();
		results.body = "the body";
		results.headers = new HttpHeaders ();
		results.status = HttpStatus.OK;

		// Mock internal calls
		setField (uut, "basePath", "https://localhost");
		when(restTemplate.execute(eq("https://localhost/the/path"), eq(HttpMethod.GET), any(), any())).thenReturn(results);

		// execute function
		Method execute = BaseStepDefinition.class.getDeclaredMethod("execute", String.class, HttpMethod.class, HttpHeaders.class, String.class);
		execute.setAccessible(true);

		// call assert function
		ResponseResults actual = (ResponseResults)execute.invoke(uut, "/the/path", HttpMethod.GET, results.headers, null);

		assertEquals("response body is not setup correctly", results.body, actual.body);
		assertEquals("response status is not setup correctly", results.status, actual.status);
		assertEquals("response status is not setup correctly", results.headers, actual.headers);
	}

	@Test
	public void verifyRawPostRequest() throws Exception {
		BaseStepDefinition uut = uut ();

		ResponseResults results = new ResponseResults ();
		results.body = "the body";
		results.headers = new HttpHeaders ();
		results.status = HttpStatus.ACCEPTED;

		// Mock internal calls
		setField (uut, "basePath", "https://localhost");
		when(restTemplate.execute(eq("https://localhost/the/path"), eq(HttpMethod.POST), any(), any())).thenReturn(results);

		// execute function
		Method execute = BaseStepDefinition.class.getDeclaredMethod("execute", String.class, HttpMethod.class, HttpHeaders.class, String.class);
		execute.setAccessible(true);

		// call assert function
		ResponseResults actual = (ResponseResults)execute.invoke(uut, "/the/path", HttpMethod.POST, results.headers, "data");

		assertEquals("response body is not setup correctly", results.body, actual.body);
		assertEquals("response status is not setup correctly", results.status, actual.status);
		assertEquals("response status is not setup correctly", results.headers, actual.headers);
	}

	@Test
	public void verifyGetRequest() throws Exception {
		BaseStepDefinition uut = uut ();

		ResponseResults results = new ResponseResults ();
		results.body = "the body";
		results.headers = new HttpHeaders ();
		results.status = HttpStatus.ACCEPTED;

		// Mock internal calls
		setField (uut, "basePath", "https://localhost");
		when(restTemplate.execute(eq("https://localhost/the/path"), eq(HttpMethod.GET), any(), any())).thenReturn(results);

		// execute function
		Method execute = BaseStepDefinition.class.getDeclaredMethod("executeGet", String.class);
		execute.setAccessible(true);

		// call assert function
		ResponseResults actual = (ResponseResults)execute.invoke(uut, "/the/path");

		assertEquals("response body is not setup correctly", results.body, actual.body);
		assertEquals("response status is not setup correctly", results.status, actual.status);
		assertEquals("response status is not setup correctly", results.headers, actual.headers);
	}

	@Test
	public void verifyPostRequest() throws Exception {
		BaseStepDefinition uut = uut ();

		ResponseResults results = new ResponseResults ();
		results.body = "the body";
		results.headers = new HttpHeaders ();
		results.status = HttpStatus.ACCEPTED;

		// Mock internal calls
		setField (uut, "basePath", "https://localhost");
		when(restTemplate.execute(eq("https://localhost/the/path"), eq(HttpMethod.POST), any(), any())).thenReturn(results);

		// execute function
		Method execute = BaseStepDefinition.class.getDeclaredMethod("executePost", String.class, String.class);
		execute.setAccessible(true);

		// call assert function
		ResponseResults actual = (ResponseResults)execute.invoke(uut, "/the/path", "data");

		assertEquals("response body is not setup correctly", results.body, actual.body);
		assertEquals("response status is not setup correctly", results.status, actual.status);
		assertEquals("response status is not setup correctly", results.headers, actual.headers);
	}

	@Test
	public void verifyPutRequest() throws Exception {
		BaseStepDefinition uut = uut ();

		ResponseResults results = new ResponseResults ();
		results.body = "the body";
		results.headers = new HttpHeaders ();
		results.status = HttpStatus.ACCEPTED;

		// Mock internal calls
		setField (uut, "basePath", "https://localhost");
		when(restTemplate.execute(eq("https://localhost/the/path"), eq(HttpMethod.PUT), any(), any())).thenReturn(results);

		// execute function
		Method execute = BaseStepDefinition.class.getDeclaredMethod("executePut", String.class, String.class);
		execute.setAccessible(true);

		// call assert function
		ResponseResults actual = (ResponseResults)execute.invoke(uut, "/the/path", "data");

		assertEquals("response body is not setup correctly", results.body, actual.body);
		assertEquals("response status is not setup correctly", results.status, actual.status);
		assertEquals("response status is not setup correctly", results.headers, actual.headers);
	}

	@Test
	public void verifyDeleteRequest() throws Exception {
		BaseStepDefinition uut = uut ();

		ResponseResults results = new ResponseResults ();
		results.body = "the body";
		results.headers = new HttpHeaders ();
		results.status = HttpStatus.ACCEPTED;

		// Mock internal calls
		setField (uut, "basePath", "https://localhost");
		when(restTemplate.execute(eq("https://localhost/the/path"), eq(HttpMethod.DELETE), any(), any())).thenReturn(results);

		// execute function
		Method execute = BaseStepDefinition.class.getDeclaredMethod("executeDelete", String.class);
		execute.setAccessible(true);

		// call assert function
		ResponseResults actual = (ResponseResults)execute.invoke(uut, "/the/path");

		assertEquals("response body is not setup correctly", results.body, actual.body);
		assertEquals("response status is not setup correctly", results.status, actual.status);
		assertEquals("response status is not setup correctly", results.headers, actual.headers);
	}

	@Test
	public void verifyGetRequestWithHeaders() throws Exception {
		BaseStepDefinition uut = uut ();

		ResponseResults results = new ResponseResults ();
		results.body = "the body";
		results.headers = new HttpHeaders ();
		results.status = HttpStatus.ACCEPTED;

		// Mock internal calls
		setField (uut, "basePath", "https://localhost");
		when(restTemplate.execute(eq("https://localhost/the/path"), eq(HttpMethod.GET), any(), any())).thenReturn(results);

		// execute function
		Method execute = BaseStepDefinition.class.getDeclaredMethod("executeGet", String.class, HttpHeaders.class);
		execute.setAccessible(true);

		// call assert function
		ResponseResults actual = (ResponseResults)execute.invoke(uut, "/the/path", results.headers);

		assertEquals("response body is not setup correctly", results.body, actual.body);
		assertEquals("response status is not setup correctly", results.status, actual.status);
		assertEquals("response status is not setup correctly", results.headers, actual.headers);
	}

	@Test
	public void verifyPostRequestWithHeaders() throws Exception {
		BaseStepDefinition uut = uut ();

		ResponseResults results = new ResponseResults ();
		results.body = "the body";
		results.headers = new HttpHeaders ();
		results.status = HttpStatus.ACCEPTED;

		// Mock internal calls
		setField (uut, "basePath", "https://localhost");
		when(restTemplate.execute(eq("https://localhost/the/path"), eq(HttpMethod.POST), any(), any())).thenReturn(results);

		// execute function
		Method execute = BaseStepDefinition.class.getDeclaredMethod("executePost", String.class, HttpHeaders.class, String.class);
		execute.setAccessible(true);

		// call assert function
		ResponseResults actual = (ResponseResults)execute.invoke(uut, "/the/path", results.headers, "data");

		assertEquals("response body is not setup correctly", results.body, actual.body);
		assertEquals("response status is not setup correctly", results.status, actual.status);
		assertEquals("response status is not setup correctly", results.headers, actual.headers);
	}

	@Test
	public void verifyPutRequestWithHeaders() throws Exception {
		BaseStepDefinition uut = uut ();

		ResponseResults results = new ResponseResults ();
		results.body = "the body";
		results.headers = new HttpHeaders ();
		results.status = HttpStatus.ACCEPTED;

		// Mock internal calls
		setField (uut, "basePath", "https://localhost");
		when(restTemplate.execute(eq("https://localhost/the/path"), eq(HttpMethod.PUT), any(), any())).thenReturn(results);

		// execute function
		Method execute = BaseStepDefinition.class.getDeclaredMethod("executePut", String.class, HttpHeaders.class, String.class);
		execute.setAccessible(true);

		// call assert function
		ResponseResults actual = (ResponseResults)execute.invoke(uut, "/the/path", results.headers, "data");

		assertEquals("response body is not setup correctly", results.body, actual.body);
		assertEquals("response status is not setup correctly", results.status, actual.status);
		assertEquals("response status is not setup correctly", results.headers, actual.headers);
	}

	@Test
	public void verifyDeleteRequestWithHeaders() throws Exception {
		BaseStepDefinition uut = uut ();

		ResponseResults results = new ResponseResults ();
		results.body = "the body";
		results.headers = new HttpHeaders ();
		results.status = HttpStatus.ACCEPTED;

		// Mock internal calls
		setField (uut, "basePath", "https://localhost");
		when(restTemplate.execute(eq("https://localhost/the/path"), eq(HttpMethod.DELETE), any(), any())).thenReturn(results);

		// execute function
		Method execute = BaseStepDefinition.class.getDeclaredMethod("executeDelete", String.class, HttpHeaders.class);
		execute.setAccessible(true);

		// call assert function
		ResponseResults actual = (ResponseResults)execute.invoke(uut, "/the/path", results.headers);

		assertEquals("response body is not setup correctly", results.body, actual.body);
		assertEquals("response status is not setup correctly", results.status, actual.status);
		assertEquals("response status is not setup correctly", results.headers, actual.headers);
	}

}




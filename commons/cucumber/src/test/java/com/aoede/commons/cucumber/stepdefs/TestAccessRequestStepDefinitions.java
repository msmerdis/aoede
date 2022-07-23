package com.aoede.commons.cucumber.stepdefs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.aoede.AccessRequestStepDefinitionsTestCaseSetup;
import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.ResponseResults;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

public class TestAccessRequestStepDefinitions extends AccessRequestStepDefinitionsTestCaseSetup {

	@Test
	public void verifySearch () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.GET, "", "/search");

		uut.search("domain", "keyword");

		verify(service).searchResults(results);
	}

	@Test
	public void verifyGetSimple () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.GET, "body", "/1");

		uut.getSimple("domain", "1");

		verify(service).accessResults(results);
	}

	@Test
	public void verifyGetSimpleCustomUrl () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);

		httpService.setUrl("/custom");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.GET, "body", "/custom");
		when(restTemplate.execute(eq("https://localhost/custom"), any(), any(), any())).thenReturn(results);

		uut.getSimple("domain", "1");

		verify(service).accessResults(results);
	}

	@Test
	public void verifyGetComposite () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.generateCompositeKey(any(DataTable.class))).thenReturn("a");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.GET, "body", "/a");

		uut.getComposite("domain", DataTable.emptyDataTable());

		verify(service).accessResults(results);
	}

	@Test
	public void verifyGetCompositePrepared () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.getCompositeKey(eq("key"))).thenReturn("b");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.GET, "body", "/b");

		uut.getCompositePrepared("domain", "key");

		verify(service).accessResults(results);
	}

	@Test
	public void verifyGetLatest () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(service.getLatestKey()).thenReturn(new JsonPrimitive(1));

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.GET, "body", "/1");

		uut.getLatest("domain");

		verify(service).accessResults(results);
	}

	@Test
	public void verifyFindAll () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.GET, "", "");

		uut.findAll("domain");

		verify(service).findAllResults(results);
	}

	@Test
	public void verifyFindAllForLatest () throws Exception {

		var uut = uut ();
		var pservice = createLatestServiceMock();
		var cservice = createLatestServiceMock();

		ResponseResults results = new ResponseResults ();
		results.body = "";
		results.headers = new HttpHeaders ();
		results.status = HttpStatus.OK;

		// Mock internal calls
		setField ((BaseStepDefinition)uut, "basePath", "https://localhost");
		when(restTemplate.execute(eq("https://localhost/the/path/1"), eq(HttpMethod.GET), any(), any())).thenReturn(results);

		when(services.getPathForService("child")).thenReturn("/the");
		when(services.getService("parent")).thenReturn(pservice);
		when(services.getService("child")).thenReturn(cservice);
		when(pservice.getName()).thenReturn("path");
		when(pservice.getLatestKey()).thenReturn(new JsonPrimitive(1));

		uut.findAllForLatest("child", "parent");

		verify(cservice).findAllResults(results);
	}
}




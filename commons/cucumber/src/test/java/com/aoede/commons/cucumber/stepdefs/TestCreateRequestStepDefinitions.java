package com.aoede.commons.cucumber.stepdefs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.aoede.CreateRequestStepDefinitionsTestCaseSetup;
import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.ResponseResults;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

public class TestCreateRequestStepDefinitions extends CreateRequestStepDefinitionsTestCaseSetup {

	@Test
	public void verifyCreateWithTable () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.generateJsonObject(any(DataTable.class))).thenReturn(new JsonObject());

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.CREATED, HttpMethod.POST, "", "");

		uut.createWithTable("domain", DataTable.emptyDataTable());

		verify(service).createResults(results);
	}

	@Test
	public void verifyCreateWithTableCustomUrl () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.generateJsonObject(any(DataTable.class))).thenReturn(new JsonObject());

		setField ((BaseStepDefinition)uut, "globalUrl", "/custom");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.CREATED, HttpMethod.POST, "", "/custom");
		when(restTemplate.execute(eq("https://localhost/custom"), any(), any(), any())).thenReturn(results);

		uut.createWithTable("domain", DataTable.emptyDataTable());

		verify(service).createResults(results);
	}

	@Test
	public void verifyCreateWithElement () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.get(eq("json"))).thenReturn(new JsonPrimitive("element value"));

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.CREATED, HttpMethod.POST, "element value", "");

		uut.createWithElement("domain", "json");

		verify(service).createResults(results);
	}

	@Test
	public void verifyCreateWithElementCustomUrl () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.get(eq("json"))).thenReturn(new JsonPrimitive("element value"));

		setField ((BaseStepDefinition)uut, "globalUrl", "/custom");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.CREATED, HttpMethod.POST, "element value", "/custom");
		when(restTemplate.execute(eq("https://localhost/custom"), any(), any(), any())).thenReturn(results);

		uut.createWithElement("domain", "json");

		verify(service).createResults(results);
	}

	@Test
	public void verifyCreateWithBody () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.CREATED, HttpMethod.POST, "body value", "");

		uut.createWithBody("domain", "body value");

		verify(service).createResults(results);
	}

	@Test
	public void verifyCreateWithBodyCustomUrl () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);

		setField ((BaseStepDefinition)uut, "globalUrl", "/custom");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.CREATED, HttpMethod.POST, "body value", "/custom");
		when(restTemplate.execute(eq("https://localhost/custom"), any(), any(), any())).thenReturn(results);

		uut.createWithBody("domain", "body value");

		verify(service).createResults(results);
	}

	@Test
	public void verifyCreateEmpty () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.generateJsonObject(any(DataTable.class))).thenReturn(new JsonObject());

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.CREATED, HttpMethod.POST, "", "");

		uut.createEmpty("domain");

		verify(service).createResults(results);
	}

}




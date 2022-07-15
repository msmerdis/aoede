package com.aoede.commons.cucumber.stepdefs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.aoede.UpdateRequestStepDefinitionsTestCaseSetup;
import com.aoede.commons.cucumber.ResponseResults;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

public class TestUpdateRequestStepDefinitions extends UpdateRequestStepDefinitionsTestCaseSetup {

	@Test
	public void verifyUpdateSimpleWithIdAndTable () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.generateJsonObject(any(DataTable.class))).thenReturn(new JsonObject());
		when(services.getPathForService(eq("domain"), eq("/1"))).thenReturn("/the/path");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.PUT, "", "/1");

		uut.updateSimpleWithIdAndTable("domain", "1", DataTable.emptyDataTable());

		verify(service).updateResults(results);
	}

	@Test
	public void verifyUpdateSimpleWithIdAndElement () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.get(eq("element"))).thenReturn(new JsonPrimitive("element value"));
		when(services.getPathForService(eq("domain"), eq("/1"))).thenReturn("/the/path");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.PUT, "element value", "/1");

		uut.updateSimpleWithIdAndElement("domain", "1", "element");

		verify(service).updateResults(results);
	}

	@Test
	public void verifyUpdateSimpleWithIdAndBody () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(services.getPathForService(eq("domain"), eq("/1"))).thenReturn("/the/path");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.PUT, "body value", "/1");

		uut.updateSimpleWithIdAndBody("domain", "1", "body value");

		verify(service).updateResults(results);
	}

	@Test
	public void verifyUpdateSimpleWithIdEmpty () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.generateJsonObject(any(DataTable.class))).thenReturn(new JsonObject());
		when(services.getPathForService(eq("domain"), eq("/1"))).thenReturn("/the/path");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.PUT, "", "/1");

		uut.updateSimpleWithIdEmpty("domain", "1");

		verify(service).updateResults(results);
	}

	@Test
	public void verifyUpdateWithCompositeIdAndTable () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.generateJsonObject(any(DataTable.class))).thenReturn(new JsonObject());
		when(services.getPathForService(eq("domain"), eq("/1"))).thenReturn("/the/path");
		when(jsonService.getCompositeKey(eq("name"))).thenReturn("a");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.PUT, "", "/a");

		uut.updateWithCompositeIdAndTable("domain", "name", DataTable.emptyDataTable());

		verify(service).updateResults(results);
	}

	@Test
	public void verifyUpdateWithCompositeIdAndElement () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.get(eq("element"))).thenReturn(new JsonPrimitive("element value"));
		when(services.getPathForService(eq("domain"), eq("/1"))).thenReturn("/the/path");
		when(jsonService.getCompositeKey(eq("name"))).thenReturn("a");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.PUT, "element value", "/a");

		uut.updateWithCompositeIdAndElement("domain", "name", "element");

		verify(service).updateResults(results);
	}

	@Test
	public void verifyUpdateWithCompositeIdAndBody () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(services.getPathForService(eq("domain"), eq("/1"))).thenReturn("/the/path");
		when(jsonService.getCompositeKey(eq("name"))).thenReturn("a");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.PUT, "body value", "/a");

		uut.updateWithCompositeIdAndBody("domain", "name", "body value");

		verify(service).updateResults(results);
	}

	@Test
	public void verifyUpdateWithCompositeIdEmpty () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.generateJsonObject(any(DataTable.class))).thenReturn(new JsonObject());
		when(services.getPathForService(eq("domain"), eq("/1"))).thenReturn("/the/path");
		when(jsonService.getCompositeKey(eq("name"))).thenReturn("a");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.PUT, "", "/a");

		uut.updateWithCompositeIdEmpty("domain", "name");

		verify(service).updateResults(results);
	}

	@Test
	public void verifyUpdateWithLatestAndTable () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.generateJsonObject(any(DataTable.class))).thenReturn(new JsonObject());
		when(services.getPathForService(eq("domain"), eq("/1"))).thenReturn("/the/path");
		when(service.getLatestKey()).thenReturn(new JsonPrimitive("b"));

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.PUT, "", "/b");

		uut.updateWithLatestAndTable("domain", DataTable.emptyDataTable());

		verify(service).updateResults(results);
	}

	@Test
	public void verifyUpdateWithLatestAndElement () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.get(eq("element"))).thenReturn(new JsonPrimitive("element value"));
		when(services.getPathForService(eq("domain"), eq("/1"))).thenReturn("/the/path");
		when(service.getLatestKey()).thenReturn(new JsonPrimitive("b"));

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.PUT, "element value", "/b");

		uut.updateWithLatestAndElement("domain", "element");

		verify(service).updateResults(results);
	}

	@Test
	public void verifyUpdateWithLatestAndBody () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(services.getPathForService(eq("domain"), eq("/1"))).thenReturn("/the/path");
		when(service.getLatestKey()).thenReturn(new JsonPrimitive("b"));

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.PUT, "body value", "/b");

		uut.updateWithLatestAndBody("domain", "body value");

		verify(service).updateResults(results);
	}

	@Test
	public void verifyUpdateWithLatestEmpty () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.generateJsonObject(any(DataTable.class))).thenReturn(new JsonObject());
		when(services.getPathForService(eq("domain"), eq("/1"))).thenReturn("/the/path");
		when(service.getLatestKey()).thenReturn(new JsonPrimitive("b"));

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.PUT, "", "/b");

		uut.updateWithLatestEmpty("domain");

		verify(service).updateResults(results);
	}

}




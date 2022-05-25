package com.aoede.commons.cucumber.stepdefs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.aoede.DeleteRequestStepDefinitionsTestCaseSetup;
import com.aoede.commons.cucumber.ResponseResults;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

public class TestDeleteRequestStepDefinitions extends DeleteRequestStepDefinitionsTestCaseSetup {

	@Test
	public void verifyDeleteSimple () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.DELETE, "", "/1");

		uut.deleteSimple("domain", "1");

		verify(service).deleteResults(results);
	}

	@Test
	public void verifyDeleteCompositePrepared () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.getCompositeKey("key")).thenReturn("f");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.DELETE, "", "/f");

		uut.deleteCompositePrepared("domain", "key");

		verify(service).deleteResults(results);
	}

	@Test
	public void verifyDeleteComposite () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.generateCompositeKey(any(DataTable.class))).thenReturn("a");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.DELETE, "", "/a");

		uut.deleteComposite("domain", DataTable.emptyDataTable());

		verify(service).deleteResults(results);
	}

	@Test
	public void verifyDeleteLatest () throws Exception {
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(service.getLatestKey()).thenReturn(new JsonPrimitive(1));

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.DELETE, "", "/1");

		uut.deleteLatest("domain");

		verify(service).deleteResults(results);
	}

}




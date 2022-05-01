package com.aoede.commons.cucumber.stepdefs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.aoede.GenericControllerStepDefsTestCaseSetup;
import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.ResponseResults;
import com.aoede.commons.cucumber.service.AbstractTestService;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

public class TestGenericControllerStepDefsRequests extends GenericControllerStepDefsTestCaseSetup {

	private ResponseResults stubRequestCall(GenericControllerStepDefs uut, AbstractTestService service, HttpStatus status, HttpMethod method, String body, String path) throws Exception {

		ResponseResults results = new ResponseResults ();
		results.body = body;
		results.headers = new HttpHeaders ();
		results.status = status;

		// Mock internal calls
		setField ((BaseStepDefinition)uut, "basePath", "https://localhost");
		when(restTemplate.execute(eq("https://localhost/the/path"), eq(method), any(), any())).thenReturn(results);
		when(services.getService(eq("domain"))).thenReturn(service);
		when(services.getPathForService(eq("domain"))).thenReturn("/the/path");
		when(services.getPathForService(eq("domain"), eq(path))).thenReturn("/the/path");

		return results;
	}

	@Test
	public void verifySearch () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.GET, "", "/search");

		uut.search("domain", "keyword");

		verify(service).searchResults(results);
	}

	@Test
	public void verifyGetSimple () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.GET, "body", "/1");

		uut.getSimple("domain", "1");

		verify(service).accessResults(results);
	}

	@Test
	public void verifyGetComposite () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(compositeIdService.generateCompositeKey(any(DataTable.class))).thenReturn("a");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.GET, "body", "/a");

		uut.getComposite("domain", DataTable.emptyDataTable());

		verify(service).accessResults(results);
	}

	@Test
	public void verifyGetCompositePrepared () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(compositeIdService.get(eq("key"))).thenReturn("b");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.GET, "body", "/b");

		uut.getCompositePrepared("domain", "key");

		verify(service).accessResults(results);
	}

	@Test
	public void verifyGetLatest () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(service.getLatestKey()).thenReturn(new JsonPrimitive(1));

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.GET, "body", "/1");

		uut.getLatest("domain");

		verify(service).accessResults(results);
	}

	@Test
	public void verifyFindAll () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.GET, "", "");

		uut.findAll("domain");

		verify(service).findAllResults(results);
	}

	@Test
	public void verifyFindAllForLatest () throws Exception {

		GenericControllerStepDefs uut = uut ();
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

	@Test
	public void verifyCreate () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonObjectService.generateJson(any(DataTable.class))).thenReturn(new JsonObject());

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.CREATED, HttpMethod.POST, "", "");

		uut.create("domain", DataTable.emptyDataTable());

		verify(service).createResults(results);
	}

	@Test
	public void verifyCreateEmpty () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonObjectService.generateJson(any(DataTable.class))).thenReturn(new JsonObject());

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.CREATED, HttpMethod.POST, "", "");

		uut.createEmpty("domain");

		verify(service).createResults(results);
	}

	@Test
	public void verifyUpdateSimple () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonObjectService.generateJson(any(DataTable.class))).thenReturn(new JsonObject());
		when(services.getPathForService(eq("domain"), eq("/1"))).thenReturn("/the/path");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.PUT, "", "/1");

		uut.updateSimple("domain", "1", DataTable.emptyDataTable());

		verify(service).updateResults(results);
	}

	@Test
	public void verifyUpdateSimpleEmpty () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonObjectService.generateJson(any(DataTable.class))).thenReturn(new JsonObject());
		when(services.getPathForService(eq("domain"), eq("/1"))).thenReturn("/the/path");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.PUT, "", "/1");

		uut.updateSimpleEmpty("domain", "1");

		verify(service).updateResults(results);
	}

	@Test
	public void verifyUpdateCompositePrepared () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonObjectService.generateJson(any(DataTable.class))).thenReturn(new JsonObject());
		when(services.getPathForService(eq("domain"), eq("/1"))).thenReturn("/the/path");
		when(compositeIdService.get(eq("name"))).thenReturn("a");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.PUT, "", "/a");

		uut.updateCompositePrepared("domain", "name", DataTable.emptyDataTable());

		verify(service).updateResults(results);
	}

	@Test
	public void verifyUpdateLatest () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonObjectService.generateJson(any(DataTable.class))).thenReturn(new JsonObject());
		when(services.getPathForService(eq("domain"), eq("/1"))).thenReturn("/the/path");
		when(service.getLatestKey()).thenReturn(new JsonPrimitive("b"));

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.PUT, "", "/b");

		uut.updateLatest("domain", DataTable.emptyDataTable());

		verify(service).updateResults(results);
	}

	@Test
	public void verifyDeleteSimple () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.DELETE, "", "/1");

		uut.deleteSimple("domain", "1");

		verify(service).deleteResults(results);
	}

	@Test
	public void verifyDeleteCompositePrepared () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(compositeIdService.get("key")).thenReturn("f");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.DELETE, "", "/f");

		uut.deleteCompositePrepared("domain", "key");

		verify(service).deleteResults(results);
	}

	@Test
	public void verifyDeleteComposite () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(compositeIdService.generateCompositeKey(any(DataTable.class))).thenReturn("a");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.DELETE, "", "/a");

		uut.deleteComposite("domain", DataTable.emptyDataTable());

		verify(service).deleteResults(results);
	}

	@Test
	public void verifyDeleteLatest () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(service.getLatestKey()).thenReturn(new JsonPrimitive(1));

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.DELETE, "", "/1");

		uut.deleteLatest("domain");

		verify(service).deleteResults(results);
	}

}




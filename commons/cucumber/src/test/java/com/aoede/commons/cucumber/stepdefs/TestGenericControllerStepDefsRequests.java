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
	public void verifyGetSimpleCustomUrl () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);

		setField ((BaseStepDefinition)uut, "globalUrl", "/custom");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.GET, "body", "/custom");
		when(restTemplate.execute(eq("https://localhost/custom"), any(), any(), any())).thenReturn(results);

		uut.getSimple("domain", "1");

		verify(service).accessResults(results);
	}

	@Test
	public void verifyGetComposite () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.generateCompositeKey(any(DataTable.class))).thenReturn("a");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.GET, "body", "/a");

		uut.getComposite("domain", DataTable.emptyDataTable());

		verify(service).accessResults(results);
	}

	@Test
	public void verifyGetCompositePrepared () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.getCompositeKey(eq("key"))).thenReturn("b");

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
	public void verifyCreateWithTable () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.generateJsonObject(any(DataTable.class))).thenReturn(new JsonObject());

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.CREATED, HttpMethod.POST, "", "");

		uut.createWithTable("domain", DataTable.emptyDataTable());

		verify(service).createResults(results);
	}

	@Test
	public void verifyCreateWithTableCustomUrl () throws Exception {
		GenericControllerStepDefs uut = uut ();
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
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.get(eq("json"))).thenReturn(new JsonPrimitive("element value"));

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.CREATED, HttpMethod.POST, "element value", "");

		uut.createWithElement("domain", "json");

		verify(service).createResults(results);
	}

	@Test
	public void verifyCreateWithElementCustomUrl () throws Exception {
		GenericControllerStepDefs uut = uut ();
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
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.CREATED, HttpMethod.POST, "body value", "");

		uut.createWithBody("domain", "body value");

		verify(service).createResults(results);
	}

	@Test
	public void verifyCreateWithBodyCustomUrl () throws Exception {
		GenericControllerStepDefs uut = uut ();
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
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.generateJsonObject(any(DataTable.class))).thenReturn(new JsonObject());

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.CREATED, HttpMethod.POST, "", "");

		uut.createEmpty("domain");

		verify(service).createResults(results);
	}

	@Test
	public void verifyUpdateSimpleWithIdAndTable () throws Exception {
		GenericControllerStepDefs uut = uut ();
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
		GenericControllerStepDefs uut = uut ();
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
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(services.getPathForService(eq("domain"), eq("/1"))).thenReturn("/the/path");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.PUT, "body value", "/1");

		uut.updateSimpleWithIdAndBody("domain", "1", "body value");

		verify(service).updateResults(results);
	}

	@Test
	public void verifyUpdateSimpleWithIdEmpty () throws Exception {
		GenericControllerStepDefs uut = uut ();
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
		GenericControllerStepDefs uut = uut ();
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
		GenericControllerStepDefs uut = uut ();
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
		GenericControllerStepDefs uut = uut ();
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
		GenericControllerStepDefs uut = uut ();
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
		GenericControllerStepDefs uut = uut ();
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
		GenericControllerStepDefs uut = uut ();
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
		GenericControllerStepDefs uut = uut ();
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
		GenericControllerStepDefs uut = uut ();
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
		when(jsonService.getCompositeKey("key")).thenReturn("f");

		ResponseResults results = stubRequestCall(uut, service, HttpStatus.OK, HttpMethod.DELETE, "", "/f");

		uut.deleteCompositePrepared("domain", "key");

		verify(service).deleteResults(results);
	}

	@Test
	public void verifyDeleteComposite () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getLatestService(eq("domain"))).thenReturn(service);
		when(jsonService.generateCompositeKey(any(DataTable.class))).thenReturn("a");

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




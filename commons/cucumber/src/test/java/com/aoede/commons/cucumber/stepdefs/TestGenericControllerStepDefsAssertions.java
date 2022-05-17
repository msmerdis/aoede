package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.aoede.GenericControllerStepDefsTestCaseSetup;
import com.aoede.commons.cucumber.ResponseResults;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

public class TestGenericControllerStepDefsAssertions extends GenericControllerStepDefsTestCaseSetup {

	private void verifySuccessfulRequest (boolean status) throws Exception {
		GenericControllerStepDefs uut = uut ();

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
		GenericControllerStepDefs uut = uut ();

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
		GenericControllerStepDefs uut = uut ();

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
		GenericControllerStepDefs uut = uut ();

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
		GenericControllerStepDefs uut = uut ();
		ResponseResults results = new ResponseResults();
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
		GenericControllerStepDefs uut = uut ();
		ResponseResults results = new ResponseResults();
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

	private void verifyArrayList (JsonArray latest, JsonArray generated, boolean status) throws Exception {
		GenericControllerStepDefs uut = uut ();

		when(services.getLatestService()).thenReturn(latestService);
		when(dataTableService.get(eq("objectName"))).thenReturn(DataTable.emptyDataTable());
		when(jsonService.generateJsonArray(any(DataTable.class), any(DataTable.class))).thenReturn(generated);
		when(latestService.getLatestArr()).thenReturn(latest);
		when(jsonService.jsonArrayMatches(any(JsonArray.class), any(JsonArray.class))).thenReturn(status);

		uut.verifyArrayList("objectName", DataTable.emptyDataTable());
	}

	@Test
	public void verifyArrayListSuccess () throws Exception {
		JsonArray latest = new JsonArray();
		JsonArray generated = new JsonArray();

		verifyArrayList(latest, generated, true);
		verify(jsonService).jsonArrayMatches(latest, generated);
	}

	@Test
	public void verifyArrayListFailure () throws Exception {
		JsonArray latest = new JsonArray();
		JsonArray generated = new JsonArray();

		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyArrayList(latest, generated, false);
		});
	}

	private void verifyNamedArrayList (String domain, JsonArray latest, JsonArray generated, boolean status) throws Exception {
		GenericControllerStepDefs uut = uut ();

		when(services.getService(domain)).thenReturn(latestService);
		when(dataTableService.get(eq("objectName"))).thenReturn(DataTable.emptyDataTable());
		when(jsonService.generateJsonArray(any(DataTable.class), any(DataTable.class))).thenReturn(generated);
		when(latestService.getLatestArr()).thenReturn(latest);
		when(jsonService.jsonArrayMatches(any(JsonArray.class), any(JsonArray.class))).thenReturn(status);

		uut.verifyArrayList(domain, "objectName", DataTable.emptyDataTable());
	}

	@Test
	public void verifyNamedArrayListSuccess () throws Exception {
		JsonArray latest = new JsonArray();
		JsonArray generated = new JsonArray();

		verifyNamedArrayList("test", latest, generated, true);
		verify(jsonService).jsonArrayMatches(latest, generated);
	}

	@Test
	public void verifyNamedArrayListFailure () throws Exception {
		JsonArray latest = new JsonArray();
		JsonArray generated = new JsonArray();

		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyNamedArrayList("test", latest, generated, false);
		});
	}

	private void verifyElementList (JsonObject latest, JsonArray generated, boolean status) throws Exception {
		GenericControllerStepDefs uut = uut ();

		when(services.getLatestService()).thenReturn(latestService);
		when(dataTableService.get(eq("tableName"))).thenReturn(DataTable.emptyDataTable());
		when(jsonService.generateJsonArray(any(DataTable.class), any(DataTable.class))).thenReturn(generated);
		when(latestService.getLatestObj()).thenReturn(latest);
		when(jsonService.jsonArrayMatches(any(JsonArray.class), any(JsonArray.class))).thenReturn(status);

		uut.verifyElementList("tableName", "element", DataTable.emptyDataTable());
	}

	@Test
	public void verifyElementListSuccess () throws Exception {
		JsonObject latest = new JsonObject();
		JsonArray generated = new JsonArray();

		latest.add("element", new JsonArray());

		verifyElementList(latest, generated, true);
	}

	@Test
	public void verifyElementListFailure () throws Exception {
		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyElementList(new JsonObject(), new JsonArray(), false);
		});
	}

	private void verifyNamedElementList (String domain, JsonObject latest, JsonArray generated, boolean status) throws Exception {
		GenericControllerStepDefs uut = uut ();

		when(services.getService(domain)).thenReturn(latestService);
		when(dataTableService.get(eq("tableName"))).thenReturn(DataTable.emptyDataTable());
		when(jsonService.generateJsonArray(any(DataTable.class), any(DataTable.class))).thenReturn(generated);
		when(latestService.getLatestObj()).thenReturn(latest);
		when(jsonService.jsonArrayMatches(any(JsonArray.class), any(JsonArray.class))).thenReturn(status);

		uut.verifyElementList(domain, "tableName", "element", DataTable.emptyDataTable());
	}

	@Test
	public void verifyNamedElementListSuccess () throws Exception {
		JsonObject latest = new JsonObject();
		JsonArray generated = new JsonArray();

		latest.add("element", new JsonArray());

		verifyNamedElementList("test", latest, generated, true);
	}

	@Test
	public void verifyNamedElementListFailure () throws Exception {
		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyNamedElementList("test", new JsonObject(), new JsonArray(), false);
		});
	}

	private void verifyElementExistInList (JsonArray latest, boolean status) throws Exception {
		GenericControllerStepDefs uut = uut ();

		when(services.getLatestService()).thenReturn(latestService);
		when(latestService.getLatestArr()).thenReturn(latest);
		when(jsonService.jsonArrayContainsObject(any(JsonArray.class), eq("id"), eq("type"), eq("value"))).thenReturn(status);

		uut.verifyElementExistInList("id", "type", "value");
	}

	@Test
	public void verifyElementExistInListSuccess () throws Exception {
		JsonArray latest = new JsonArray();
		verifyElementExistInList(latest, true);
		verify(jsonService).jsonArrayContainsObject(latest, "id", "type", "value");
	}

	@Test
	public void verifyElementExistInListFailure () throws Exception {
		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyElementExistInList(new JsonArray(), false);
		});
	}

	private void verifyElementDoesNotExistInList (JsonArray latest, boolean status) throws Exception {
		GenericControllerStepDefs uut = uut ();

		when(services.getLatestService()).thenReturn(latestService);
		when(latestService.getLatestArr()).thenReturn(latest);
		when(jsonService.jsonArrayContainsObject(any(JsonArray.class), eq("id"), eq("type"), eq("value"))).thenReturn(status);

		uut.verifyElementDoesNotExistInList("id", "type", "value");
	}

	@Test
	public void verifyElementDoesNotExistInListSuccess () throws Exception {
		JsonArray latest = new JsonArray();
		verifyElementDoesNotExistInList(latest, false);
		verify(jsonService).jsonArrayContainsObject(latest, "id", "type", "value");
	}

	@Test
	public void verifyElementDoesNotExistInListFailure () throws Exception {
		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyElementDoesNotExistInList(new JsonArray(), true);
		});
	}

	private void verifyLatestElementExistsInList (JsonArray array, JsonElement key, boolean status) throws Exception {
		GenericControllerStepDefs uut = uut ();

		when(services.getLatestService()).thenReturn(latestService);
		when(latestService.getLatestArr()).thenReturn(array);
		when(latestService.getKeyName()).thenReturn("key");
		when(latestService.getLatestKey()).thenReturn(key);
		when(jsonService.jsonArrayContainsObjectWithElement(eq(array), eq("key"), eq(key))).thenReturn(status);

		uut.verifyLatestElementExistsInList("domain");
	}

	@Test
	public void verifyLatestElementExistsInListSuccess () throws Exception {
		JsonArray array = new JsonArray();
		JsonElement key = new JsonPrimitive("latest");

		verifyLatestElementExistsInList(array, key, true);
		verify(jsonService).jsonArrayContainsObjectWithElement(eq(array), eq("key"), eq(key));
	}

	@Test
	public void verifyLatestElementExistsInListFailure () throws Exception {
		JsonArray array = new JsonArray();
		JsonElement key = new JsonPrimitive("latest");

		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyLatestElementExistsInList(array, key, false);
		});
	}

	private void verifyLatestElementDoesNotExistInList (JsonArray array, JsonElement key, boolean status) throws Exception {
		GenericControllerStepDefs uut = uut ();

		when(services.getLatestService()).thenReturn(latestService);
		when(latestService.getLatestArr()).thenReturn(array);
		when(latestService.getKeyName()).thenReturn("key");
		when(latestService.getLatestKey()).thenReturn(key);
		when(jsonService.jsonArrayContainsObjectWithElement(eq(array), eq("key"), eq(key))).thenReturn(status);

		uut.verifyLatestElementDoesNotExistInList("domain");
	}

	@Test
	public void verifyLatestElementDoesNotExistInListSuccess () throws Exception {
		JsonArray array = new JsonArray();
		JsonElement key = new JsonPrimitive("latest");

		verifyLatestElementDoesNotExistInList(array, key, false);
		verify(jsonService).jsonArrayContainsObjectWithElement(eq(array), eq("key"), eq(key));
	}

	@Test
	public void verifyLatestElementDoesNotExistInListFailure () throws Exception {
		JsonArray array = new JsonArray();
		JsonElement key = new JsonPrimitive("latest");

		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyLatestElementDoesNotExistInList(array, key, true);
		});
	}

	private void verifyDependentDomains (boolean status) throws Exception {
		GenericControllerStepDefs uut = uut ();
		var pservice = createLatestServiceMock();
		var cservice = createLatestServiceMock();
		JsonObject obj = new JsonObject();
		JsonArray  arr = new JsonArray();

		obj.add("element", arr);

		when(services.getService("pname")).thenReturn(pservice);
		when(services.getService("cname")).thenReturn(cservice);
		when(pservice.getLatestObj()).thenReturn(obj);
		when(cservice.getKeyName()).thenReturn("key");
		when(cservice.getLatestKey()).thenReturn(new JsonPrimitive(1));
		when(jsonService.jsonArrayContainsObjectWithElement(eq(arr), eq("key"), eq(new JsonPrimitive(1)))).thenReturn(status);

		uut.verifyDependentDomains("pname", "cname", "element");
	}

	@Test
	public void verifyDependentDomainsSuccess () throws Exception {
		verifyDependentDomains(true);
	}

	@Test
	public void verifyDependentDomainsFailure () throws Exception {
		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyDependentDomains(false);
		});
	}

	private void verifyResultCount (int expected, int count) throws Exception {
		GenericControllerStepDefs uut = uut ();
		JsonArray array = new JsonArray();
		var service = createLatestServiceMock();

		for (int i = 0; i < count; i += 1) {
			array.add(i);
		}

		when(services.getService(any())).thenReturn(service);
		when(service.getLatestArr()).thenReturn(array);

		uut.verifyResultCount("domain", expected);
	}

	@Test
	public void verifyResultCountSuccess () throws Exception {
		verifyResultCount(0, 0);
		verifyResultCount(1, 1);
		verifyResultCount(2, 2);
	}

	@Test
	public void verifyResultCountFailure () throws Exception {
		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyResultCount(0, 1);
		});

		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyResultCount(1, 0);
		});

		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyResultCount(2, 3);
		});
	}

	@Test
	public void verifyResultCountNoArray () throws Exception {
		GenericControllerStepDefs uut = uut ();
		var service = createLatestServiceMock();

		when(services.getService(any())).thenReturn(service);
		when(service.getLatestArr()).thenReturn(null);

		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			uut.verifyResultCount("domain", 0);
		});
	}

	private void verifyElement (boolean status) throws Exception {
		GenericControllerStepDefs uut = uut ();
		JsonObject object = new JsonObject();

		when(services.getLatestService()).thenReturn(latestService);
		when(latestService.getLatestObj()).thenReturn(object);
		when(jsonService.jsonObjectMatches(eq(object), eq(DataTable.emptyDataTable()))).thenReturn(status);

		uut.verifyElement(DataTable.emptyDataTable());
	}

	@Test
	public void verifyNamedElementSuccess () throws Exception {
		verifyNamedElement("test", true);
	}

	@Test
	public void verifyNamedElementFailure () throws Exception {
		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyNamedElement("fail", false);
		});
	}

	private void verifyNamedElement (String domain, boolean status) throws Exception {
		GenericControllerStepDefs uut = uut ();
		JsonObject object = new JsonObject();

		when(services.getService(domain)).thenReturn(latestService);
		when(latestService.getLatestObj()).thenReturn(object);
		when(jsonService.jsonObjectMatches(eq(object), eq(DataTable.emptyDataTable()))).thenReturn(status);

		uut.verifyElement(domain, DataTable.emptyDataTable());
	}

	@Test
	public void verifyElementSuccess () throws Exception {
		verifyElement(true);
	}

	@Test
	public void verifyElementFailure () throws Exception {
		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyElement(false);
		});
	}

	private void verifyArraySize (int expected, int size) throws Exception {
		GenericControllerStepDefs uut = uut ();
		JsonObject obj = new JsonObject();
		JsonArray  arr = new JsonArray();

		for (int i = 0; i < size; i += 1)
			arr.add(i);
		obj.add("array", arr);

		var service = createLatestServiceMock();

		when(services.getService(eq("domain"))).thenReturn(service);
		when(service.getLatestObj()).thenReturn(obj);

		uut.verifyArraySize("domain", "array", expected);
	}

	@Test
	public void verifyArraySizeSuccess () throws Exception {
		verifyArraySize(0, 0);
		verifyArraySize(1, 1);
		verifyArraySize(5, 5);
	}

	@Test
	public void verifyArraySizeFailure () throws Exception {
		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyArraySize(0, 1);
		});

		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyArraySize(1, 0);
		});

		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyArraySize(2, 3);
		});
	}

	@Test
	public void verifyArraySizeNoObject () throws Exception {
		GenericControllerStepDefs uut = uut ();

		var service = createLatestServiceMock();

		when(services.getService(eq("domain"))).thenReturn(service);
		when(service.getLatestObj()).thenReturn(null);

		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			uut.verifyArraySize("domain", "array", 0);
		});
	}

	@Test
	public void verifyArraySizeNoElement () throws Exception {
		GenericControllerStepDefs uut = uut ();
		JsonObject obj = new JsonObject();

		var service = createLatestServiceMock();

		when(services.getService(eq("domain"))).thenReturn(service);
		when(service.getLatestObj()).thenReturn(obj);

		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			uut.verifyArraySize("domain", "array", 0);
		});
	}

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




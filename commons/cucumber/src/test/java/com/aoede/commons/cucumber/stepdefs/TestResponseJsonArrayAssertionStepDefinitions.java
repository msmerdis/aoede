package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.aoede.ResponseJsonArrayAssertionStepDefinitionsTestCaseSetup;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

public class TestResponseJsonArrayAssertionStepDefinitions extends ResponseJsonArrayAssertionStepDefinitionsTestCaseSetup {

	private void verifyArrayList (JsonArray latest, JsonArray generated, boolean status) throws Exception {
		var uut = uut ();

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
		var uut = uut ();

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

	private void verifyElementExistInList (JsonArray latest, boolean status) throws Exception {
		var uut = uut ();

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
		var uut = uut ();

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
		var uut = uut ();

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
		var uut = uut ();

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

	private void verifyResultCount (int expected, int count) throws Exception {
		var uut = uut ();
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
		var uut = uut ();
		var service = createLatestServiceMock();

		when(services.getService(any())).thenReturn(service);
		when(service.getLatestArr()).thenReturn(null);

		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			uut.verifyResultCount("domain", 0);
		});
	}

	private void verifyDependentDomains (boolean status) throws Exception {
		var uut = uut ();
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

}




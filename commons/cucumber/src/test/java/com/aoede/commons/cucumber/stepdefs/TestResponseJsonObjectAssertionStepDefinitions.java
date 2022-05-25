package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.aoede.ResponseJsonObjectAssertionStepDefinitionsTestCaseSetup;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

public class TestResponseJsonObjectAssertionStepDefinitions extends ResponseJsonObjectAssertionStepDefinitionsTestCaseSetup {

	private void verifyElement (boolean status) throws Exception {
		var uut = uut ();
		JsonObject object = new JsonObject();

		when(services.getLatestService()).thenReturn(latestService);
		when(latestService.getLatestObj()).thenReturn(object);
		when(jsonService.jsonObjectMatches(eq(object), eq(DataTable.emptyDataTable()))).thenReturn(status);

		uut.verifyElement(DataTable.emptyDataTable());
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
		var uut = uut ();
		JsonObject object = new JsonObject();

		when(services.getService(domain)).thenReturn(latestService);
		when(latestService.getLatestObj()).thenReturn(object);
		when(jsonService.jsonObjectMatches(eq(object), eq(DataTable.emptyDataTable()))).thenReturn(status);

		uut.verifyElement(domain, DataTable.emptyDataTable());
	}

	private void verifyJson (boolean status) throws Exception {
		var uut = uut ();
		JsonObject object = new JsonObject();
		JsonObject json = new JsonObject();

		object.add("type", new JsonPrimitive("object"));
		json.add("type", new JsonPrimitive("json"));

		when(services.getLatestService()).thenReturn(latestService);
		when(latestService.getLatestObj()).thenReturn(object);
		when(jsonService.get(eq("json"))).thenReturn(json);
		when(jsonService.jsonObjectMatches(eq(object), eq(json))).thenReturn(status);

		uut.verifyJson("json");
	}

	@Test
	public void verifyJsonSuccess () throws Exception {
		verifyJson(true);
	}

	@Test
	public void verifyJsonFailure () throws Exception {
		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyJson(false);
		});
	}

	private void verifyNamedJson (boolean status) throws Exception {
		var uut = uut ();
		JsonObject object = new JsonObject();
		JsonObject json = new JsonObject();

		object.add("type", new JsonPrimitive("object"));
		json.add("type", new JsonPrimitive("json"));

		when(services.getService(eq("domain"))).thenReturn(latestService);
		when(latestService.getLatestObj()).thenReturn(object);
		when(jsonService.get(eq("json"))).thenReturn(json);
		when(jsonService.jsonObjectMatches(eq(object), eq(json))).thenReturn(status);

		uut.verifyJson("domain", "json");
	}

	@Test
	public void verifyNamedJsonSuccess () throws Exception {
		verifyNamedJson(true);
	}

	@Test
	public void verifyNamedJsonFailure () throws Exception {
		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			verifyNamedJson(false);
		});
	}

	private void verifyElementList (JsonObject latest, JsonArray generated, boolean status) throws Exception {
		var uut = uut ();

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
		var uut = uut ();

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

	private void verifyArraySize (int expected, int size) throws Exception {
		var uut = uut ();
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
		var uut = uut ();

		var service = createLatestServiceMock();

		when(services.getService(eq("domain"))).thenReturn(service);
		when(service.getLatestObj()).thenReturn(null);

		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			uut.verifyArraySize("domain", "array", 0);
		});
	}

	@Test
	public void verifyArraySizeNoElement () throws Exception {
		var uut = uut ();
		JsonObject obj = new JsonObject();

		var service = createLatestServiceMock();

		when(services.getService(eq("domain"))).thenReturn(service);
		when(service.getLatestObj()).thenReturn(obj);

		assertThrows ("assertion not performed succesfully", AssertionError.class, () -> {
			uut.verifyArraySize("domain", "array", 0);
		});
	}

}




package com.aoede.commons.cucumber.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;

import com.aoede.JsonServiceImplTestCaseSetup;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

public class TestJsonServiceImplGenerateJson extends JsonServiceImplTestCaseSetup {

	@Test
	public void verifyGenerateSimpleJson () throws Exception {
		DataTable table = buildDataTable (List.of(
			List.of( "id" , "integer", "1"),
			List.of("name", "string", "test")
		));

		JsonObject object = uut ().generateJsonObject(table);

		assertEquals ("json is not generated correctly", "{\"id\":1,\"name\":\"test\"}", object.toString());
	}

	@Test
	public void verifyGenerateLongJson () throws Exception {
		DataTable table = buildDataTable (List.of(
			List.of( "id" , "int", "1"),
			List.of("data", "long", "2")
		));

		JsonObject object = uut ().generateJsonObject(table);

		assertEquals ("json is not generated correctly", "{\"id\":1,\"data\":2}", object.toString());
	}

	@Test
	public void verifyGenerateBoolJson () throws Exception {
		DataTable table = buildDataTable (List.of(
			List.of("id", "fraction", "7/8"),
			List.of("nothing", "null", "")
		));

		JsonObject object = uut ().generateJsonObject(table);

		assertEquals ("json is not generated correctly", "{\"id\":{\"numerator\":7,\"denominator\":8},\"nothing\":null}", object.toString());
	}

	@Test
	public void verifyGenerateCompositeIdJsonDirect () throws Exception {
		var uut = uut ();

		uut.put("id name", new JsonPrimitive("id value"));

		DataTable table = buildDataTable (List.of(
			List.of("id", "compositeId", "id name")
		));

		JsonObject object = uut.generateJsonObject(table);

		assertEquals ("json is not generated correctly", "{\"id\":\"id value\"}", object.toString());
	}

	@Test
	public void verifyGenerateCompositeIdJsonCalculated () throws Exception {
		var uut = uut ();

		DataTable id = buildDataTable (List.of(
			List.of("pid", "integer", "100"),
			List.of("cid",  "string", "100")
		));

		uut.putCompositeKey("id name", id);

		DataTable table = buildDataTable (List.of(
			List.of("id", "compositeId", "id name")
		));

		JsonObject object = uut.generateJsonObject(table);

		assertEquals ("json is not generated correctly", "{\"id\":\"eyJwaWQiOjEwMCwiY2lkIjoiMTAwIn0g\"}", object.toString());
	}

	@Test
	public void verifyGenerateKeyJson () throws Exception {
		when (abstractTestServiceDiscoveryService.getService(eq("serviceName"))).thenReturn(
			new AbstractTestServiceImpl () {

				@Override
				public String getName() {
					return null;
				}
				@Override
				public String getPath() {
					return null;
				}

				@Override
				public String getKeyName() {
					return null;
				}

				@Override
				public JsonElement getLatestKey() {
					return new JsonPrimitive ("latest service key");
				}

			}
		);

		DataTable table = buildDataTable (List.of(
			List.of("id", "key", "serviceName")
		));

		JsonObject object = uut ().generateJsonObject(table);

		assertEquals ("json is not generated correctly", "{\"id\":\"latest service key\"}", object.toString());
	}

	@Test
	public void verifyGenerateJsonJson () throws Exception {
		JsonServiceImpl uut = uut ();
		JsonObject theJson = new JsonObject();

		theJson.add("nada", JsonNull.INSTANCE);

		uut.put("json name", theJson);

		DataTable table = buildDataTable (List.of(
			List.of("obj", "json", "json name")
		));

		JsonObject object = uut.generateJsonObject(table);

		assertEquals ("json is not generated correctly", "{\"obj\":{\"nada\":null}}", object.toString());
	}

	@Test
	public void verifyGenerateRandomJson () throws Exception {
		random.setSeed(0);

		DataTable table = buildDataTable (List.of(
			List.of("data", "random string", "data_{string:12}")
		));

		JsonObject object = uut().generateJsonObject(table);

		assertEquals ("json is not generated correctly", "{\"data\":\"data_ly4qeYTuM22G\"}", object.toString());
	}

	@Test
	public void verifyGenerateJsonColumnNumber1 () throws Exception {
		random.setSeed(0);

		DataTable table = buildDataTable (List.of(
			List.of("data")
		));

		assertThrows ("json is not generated correctly", AssertionError.class, () -> {
			uut().generateJsonObject(table);
		});
	}

	@Test
	public void verifyGenerateJsonColumnNumber2 () throws Exception {
		random.setSeed(0);

		DataTable table = buildDataTable (List.of(
			List.of("data", "null")
		));

		assertThrows ("json is not generated correctly", AssertionError.class, () -> {
			uut().generateJsonObject(table);
		});
	}

	@Test
	public void verifyGenerateJsonColumnNumber4 () throws Exception {
		random.setSeed(0);

		DataTable table = buildDataTable (List.of(
			List.of("data", "null", "", "")
		));

		assertThrows ("json is not generated correctly", AssertionError.class, () -> {
			uut().generateJsonObject(table);
		});
	}

	@Test
	public void verifyGenerateJsonInvalidType () throws Exception {
		random.setSeed(0);

		DataTable table = buildDataTable (List.of(
			List.of("data", "invalid", "")
		));

		assertThrows ("json is not generated correctly", AssertionError.class, () -> {
			uut().generateJsonObject(table);
		});
	}

}




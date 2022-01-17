package com.aoede.commons.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Locale;

import org.junit.Test;

import com.aoede.JsonObjectServiceImplTestCaseSetup;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableTypeRegistry;
import io.cucumber.datatable.DataTableTypeRegistryTableConverter;

public class TestJsonObjectServiceImplGenerateJson extends JsonObjectServiceImplTestCaseSetup {

	@Test
	public void verifyGenerateSimpleJson () throws Exception {
		DataTable table = DataTable.create(List.of(
			List.of( "id" , "integer", "1"),
			List.of("name", "string", "test")
		), new DataTableTypeRegistryTableConverter (
			new DataTableTypeRegistry (Locale.ENGLISH)
		));

		JsonObject object = uut ().generateJson(table);

		assertEquals ("json is not generated correctly", "{\"id\":1,\"name\":\"test\"}", object.toString());
	}

	@Test
	public void verifyGenerateLongJson () throws Exception {
		DataTable table = DataTable.create(List.of(
			List.of( "id" , "int", "1"),
			List.of("data", "long", "2")
		), new DataTableTypeRegistryTableConverter (
			new DataTableTypeRegistry (Locale.ENGLISH)
		));

		JsonObject object = uut ().generateJson(table);

		assertEquals ("json is not generated correctly", "{\"id\":1,\"data\":2}", object.toString());
	}

	@Test
	public void verifyGenerateBoolJson () throws Exception {
		DataTable table = DataTable.create(List.of(
			List.of("id", "fraction", "7/8"),
			List.of("nothing", "null", "")
		), new DataTableTypeRegistryTableConverter (
			new DataTableTypeRegistry (Locale.ENGLISH)
		));

		JsonObject object = uut ().generateJson(table);

		assertEquals ("json is not generated correctly", "{\"id\":{\"numerator\":7,\"denominator\":8},\"nothing\":null}", object.toString());
	}

	@Test
	public void verifyGenerateCompositeIdJson () throws Exception {
		when (compositeIdService.containsKey(eq("id name"))).thenReturn(true);
		when (compositeIdService.get(eq("id name"))).thenReturn("id value");

		DataTable table = DataTable.create(List.of(
			List.of("id", "compositeId", "id name")
		), new DataTableTypeRegistryTableConverter (
			new DataTableTypeRegistry (Locale.ENGLISH)
		));

		JsonObject object = uut ().generateJson(table);

		assertEquals ("json is not generated correctly", "{\"id\":\"id value\"}", object.toString());
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

		DataTable table = DataTable.create(List.of(
			List.of("id", "key", "serviceName")
		), new DataTableTypeRegistryTableConverter (
			new DataTableTypeRegistry (Locale.ENGLISH)
		));

		JsonObject object = uut ().generateJson(table);

		assertEquals ("json is not generated correctly", "{\"id\":\"latest service key\"}", object.toString());
	}

	@Test
	public void verifyGenerateJsonJson () throws Exception {
		JsonObjectServiceImpl uut = uut ();
		JsonObject theJson = new JsonObject();

		theJson.add("nada", JsonNull.INSTANCE);

		uut.put("json name", theJson);

		DataTable table = DataTable.create(List.of(
			List.of("obj", "json", "json name")
		), new DataTableTypeRegistryTableConverter (
			new DataTableTypeRegistry (Locale.ENGLISH)
		));

		JsonObject object = uut.generateJson(table);

		assertEquals ("json is not generated correctly", "{\"obj\":{\"nada\":null}}", object.toString());
	}

	@Test
	public void verifyGenerateRandomJson () throws Exception {
		random.setSeed(0);

		DataTable table = DataTable.create(List.of(
			List.of("data", "random string", "data_{string:12}")
		), new DataTableTypeRegistryTableConverter (
			new DataTableTypeRegistry (Locale.ENGLISH)
		));

		JsonObject object = uut().generateJson(table);

		assertEquals ("json is not generated correctly", "{\"data\":\"data_ly4qeYTuM22G\"}", object.toString());
	}

}




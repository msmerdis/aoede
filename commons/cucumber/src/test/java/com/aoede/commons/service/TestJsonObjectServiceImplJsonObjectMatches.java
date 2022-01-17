package com.aoede.commons.service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Locale;

import org.junit.Test;

import com.aoede.JsonObjectServiceImplTestCaseSetup;
import com.google.gson.JsonObject;

import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableTypeRegistry;
import io.cucumber.datatable.DataTableTypeRegistryTableConverter;

public class TestJsonObjectServiceImplJsonObjectMatches extends JsonObjectServiceImplTestCaseSetup {

	@Test
	public void verifyJsonMatch () throws Exception {
		DataTable table = DataTable.create(List.of(
			List.of( "id" , "integer", "1"),
			List.of("name", "string", "test")
		), new DataTableTypeRegistryTableConverter (
			new DataTableTypeRegistry (Locale.ENGLISH)
		));

		JsonObject object = uut ().generateJson(table);

		assertEquals ("json is not generated correctly", "{\"id\":1,\"name\":\"test\"}", object.toString());
	}

}




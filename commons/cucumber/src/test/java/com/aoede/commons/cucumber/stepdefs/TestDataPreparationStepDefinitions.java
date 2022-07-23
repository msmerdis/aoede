package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;

import com.aoede.DataPreparationStepDefinitionsTestCaseSetup;
import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.service.DataTableServiceImpl;
import com.aoede.commons.cucumber.service.JsonServiceImpl;

import io.cucumber.datatable.DataTable;

public class TestDataPreparationStepDefinitions extends DataPreparationStepDefinitionsTestCaseSetup {

	private JsonServiceImpl jsonServiceImpl;

	@Override
	protected DataPreparationStepDefinitions uut () throws Exception {
		var uut = super.uut();

		// use actual json object service for the test
		jsonServiceImpl = new JsonServiceImpl(null);
		setField ((BaseStepDefinition)uut, "jsonService", jsonServiceImpl);

		return uut;
	}

	@Test
	public void verifyPreparationOfCompositeId() throws Exception {
		var uut = uut ();

		DataTable table = buildDataTable(List.of(
			List.of("pid", "int", "1"),
			List.of("cid", "int", "1")
		));

		// execute function
		uut.prepareCompositeId("cid", table);

		assertEquals("composite key was not prepared correcty", "eyJwaWQiOjEsImNpZCI6MX0g", jsonServiceImpl.getCompositeKey("cid"));
	}

	@Test
	public void verifyPreparationOfSimpleJsonObject() throws Exception {
		var uut = uut ();

		// execute function
		uut.prepareJsonObject("json", "test", "string");

		assertEquals("composite key was not prepared correcty", "test", jsonServiceImpl.getCompositeKey("json"));
	}

	@Test
	public void verifyPreparationOfJsonObject() throws Exception {
		var uut = uut ();

		DataTable table = buildDataTable(List.of(
			List.of("pid", "int", "1"),
			List.of("cid", "int", "1")
		));

		// execute function
		uut.prepareJsonObject("json", table);

		assertEquals("json was not prepared correcty", "{\"pid\":1,\"cid\":1}", jsonServiceImpl.get("json").toString());
	}

	@Test
	public void verifyPreparationOfEmptyJsonObject() throws Exception {
		var uut = uut ();

		// execute function
		uut.prepareEmptyJsonObject("json");

		assertEquals("json was not prepared correcty", "{}", jsonServiceImpl.get("json").toString());
	}

	@Test
	public void verifyPreparationOfJsonArray() throws Exception {
		var uut = uut ();

		DataTable table = buildDataTable(List.of(
			List.of("int", "1"),
			List.of("int", "2"),
			List.of("string", "a")
		));

		// execute function
		uut.prepareJsonArray("json", table);

		assertEquals("json array was not prepared correcty", "[1,2,\"a\"]", jsonServiceImpl.get("json").toString());
	}

	@Test
	public void verifyPreparationOfJsonObjectArray() throws Exception {
		var uut = uut ();

		DataTable template = buildDataTable(List.of(
			List.of("name1", "name2", "name3"),
			List.of("int",   "bool",  "string")
		));

		DataTable table = buildDataTable(List.of(
			List.of("name1", "name2", "name3"),
			List.of("1", "true",  "value1"),
			List.of("2", "false", "value2"),
			List.of("3", "true",  "value3")
		));

		when(dataTableService.get(eq("template"))).thenReturn(template);

		// execute function
		uut.prepareJsonArray("json", "template", table);

		assertEquals(
			"json array was not prepared correcty",
			"[{\"name1\":1,\"name2\":true,\"name3\":\"value1\"},{\"name1\":2,\"name2\":false,\"name3\":\"value2\"},{\"name1\":3,\"name2\":true,\"name3\":\"value3\"}]",
			jsonServiceImpl.get("json").toString()
		);
	}

	@Test
	public void verifyPreparationOfDataTable() throws Exception {
		var uut = uut ();

		// use actual json object service for the test
		var dataTableServiceImpl = new DataTableServiceImpl ();
		setField ((BaseStepDefinition)uut, "dataTableService", dataTableServiceImpl);

		DataTable table = buildDataTable(List.of(
			List.of("pid", "int", "1"),
			List.of("cid", "int", "1")
		));

		// execute function
		uut.prepareDataTable("table", table);

		assertEquals("data table was not prepared correcty", table, dataTableServiceImpl.get("table"));
	}

	@Test
	public void verifyPreparationOfUrlTable() throws Exception {
		var uut = uut ();

		DataTable table = buildDataTable(List.of(
			List.of("int", "1"),
			List.of("int", "1")
		));

		// execute function
		uut.prepareUrl(table);

		assertEquals("composite key was not prepared correcty", "/1/1", httpService.getUrl());
	}

	@Test
	public void verifyPreparationOfUrlString() throws Exception {
		var uut = uut ();

		// execute function
		uut.prepareUrl("/url");

		assertEquals("composite key was not prepared correcty", "/url", httpService.getUrl());
	}

}




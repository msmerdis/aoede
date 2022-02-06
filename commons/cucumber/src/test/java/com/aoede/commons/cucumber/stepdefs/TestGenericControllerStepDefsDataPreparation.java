package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;

import com.aoede.GenericControllerStepDefsTestCaseSetup;
import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.service.CompositeIdServiceImpl;
import com.aoede.commons.cucumber.service.DataTableServiceImpl;
import com.aoede.commons.cucumber.service.JsonObjectServiceImpl;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

public class TestGenericControllerStepDefsDataPreparation extends GenericControllerStepDefsTestCaseSetup {

	@Test
	public void verifyPreparationOfCompositeId() throws Exception {
		GenericControllerStepDefs uut = uut ();

		// use actual json object service for the test
		CompositeIdServiceImpl compositeIdServiceImpl = new CompositeIdServiceImpl ();
		setField ((BaseStepDefinition)uut, "compositeIdService", compositeIdServiceImpl);

		Field field = CompositeIdServiceImpl.class.getDeclaredField("jsonObjectService");
		field.setAccessible(true);
		field.set(compositeIdServiceImpl, jsonObjectService);

		JsonObject object = new JsonObject();

		object.add("pid", new JsonPrimitive(1));
		object.add("cid", new JsonPrimitive(1));

		when(jsonObjectService.generateJson(any())).thenReturn(object);

		DataTable table = buildDataTable(List.of(
			List.of("pid", "int", "1"),
			List.of("cid", "int", "1")
		));

		// execute function
		uut.prepareCompositeId("cid", table);

		assertEquals("composite key was not prepared correcty", "eyJwaWQiOjEsImNpZCI6MX0g", compositeIdServiceImpl.get("cid").toString());
	}

	@Test
	public void verifyPreparationOfJson() throws Exception {
		GenericControllerStepDefs uut = uut ();

		// use actual json object service for the test
		JsonObjectServiceImpl jsonObjectServiceImpl = new JsonObjectServiceImpl(null, new CompositeIdServiceImpl ());
		setField ((BaseStepDefinition)uut, "jsonObjectService", jsonObjectServiceImpl);

		DataTable table = buildDataTable(List.of(
			List.of("pid", "int", "1"),
			List.of("cid", "int", "1")
		));

		// execute function
		uut.prepareJson("json", table);

		assertEquals("json was not prepared correcty", "{\"pid\":1,\"cid\":1}", jsonObjectServiceImpl.get("json").toString());
	}

	@Test
	public void verifyPreparationOfDataTable() throws Exception {
		GenericControllerStepDefs uut = uut ();

		// use actual json object service for the test
		DataTableServiceImpl dataTableServiceImpl = new DataTableServiceImpl ();
		setField ((BaseStepDefinition)uut, "dataTableService", dataTableServiceImpl);

		DataTable table = buildDataTable(List.of(
			List.of("pid", "int", "1"),
			List.of("cid", "int", "1")
		));

		// execute function
		uut.prepareDataTable("table", table);

		assertEquals("data table was not prepared correcty", table, dataTableServiceImpl.get("table"));
	}

}




package com.aoede.commons.cucumber.stepdefs;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.aoede.GenericControllerStepDefsTestCaseSetup;
import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.service.DataTableServiceImpl;
import com.aoede.commons.cucumber.service.JsonServiceImpl;

import io.cucumber.datatable.DataTable;

public class TestGenericControllerStepDefsDataPreparation extends GenericControllerStepDefsTestCaseSetup {

	@Test
	public void verifyPreparationOfCompositeId() throws Exception {
		GenericControllerStepDefs uut = uut ();

		// use actual json object service for the test
		JsonServiceImpl jsonServiceImpl = new JsonServiceImpl(null);
		setField ((BaseStepDefinition)uut, "jsonService", jsonServiceImpl);

		DataTable table = buildDataTable(List.of(
			List.of("pid", "int", "1"),
			List.of("cid", "int", "1")
		));

		// execute function
		uut.prepareCompositeId("cid", table);

		assertEquals("composite key was not prepared correcty", "eyJwaWQiOjEsImNpZCI6MX0g", jsonServiceImpl.getCompositeKey("cid"));
	}

	@Test
	public void verifyPreparationOfJson() throws Exception {
		GenericControllerStepDefs uut = uut ();

		// use actual json object service for the test
		JsonServiceImpl jsonServiceImpl = new JsonServiceImpl(null);
		setField ((BaseStepDefinition)uut, "jsonService", jsonServiceImpl);

		DataTable table = buildDataTable(List.of(
			List.of("pid", "int", "1"),
			List.of("cid", "int", "1")
		));

		// execute function
		uut.prepareJson("json", table);

		assertEquals("json was not prepared correcty", "{\"pid\":1,\"cid\":1}", jsonServiceImpl.get("json").toString());
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




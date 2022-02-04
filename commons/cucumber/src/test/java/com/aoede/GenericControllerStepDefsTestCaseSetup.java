package com.aoede;

import java.lang.reflect.Field;

import org.mockito.Mockito;

import com.aoede.commons.cucumber.service.AbstractTestService;
import com.aoede.commons.cucumber.stepdefs.GenericControllerStepDefs;

public class GenericControllerStepDefsTestCaseSetup extends BaseStepDefinitionTestCaseSetup {

	protected AbstractTestService latestService;

	// unit under test
	@Override
	protected GenericControllerStepDefs uut () throws Exception {
		GenericControllerStepDefs uut = new GenericControllerStepDefs ();

		latestService = createLatestServiceMock ();

		super.setField (uut, "restTemplate", this.restTemplate);
		super.setField (uut, "serverProperties", this.serverProperties);
		super.setField (uut, "services", this.services);
		super.setField (uut, "testCaseIdTrackerService", this.testCaseIdTrackerService);
		super.setField (uut, "jsonObjectService", this.jsonObjectService);
		super.setField (uut, "compositeIdService", this.compositeIdService);
		super.setField (uut, "dataTableService", this.dataTableService);

		setField (uut, "latestService", this.latestService);

		return uut;
	}

	protected void setField (GenericControllerStepDefs uut, String fieldName, Object value) throws Exception {
		Field field = GenericControllerStepDefs.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(uut, value);
	}

	protected Object getField (GenericControllerStepDefs uut, String fieldName) throws Exception {
		Field field = GenericControllerStepDefs.class.getDeclaredField(fieldName);

		field.setAccessible(true);

		return field.get(uut);
	}

	protected AbstractTestService createLatestServiceMock() {
		return Mockito.mock(AbstractTestService.class);
	}

}




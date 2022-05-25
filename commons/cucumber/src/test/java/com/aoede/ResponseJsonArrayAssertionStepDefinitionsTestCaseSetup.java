package com.aoede;

import com.aoede.commons.cucumber.service.AbstractTestService;
import com.aoede.commons.cucumber.stepdefs.ResponseJsonArrayAssertionStepDefinitions;

public class ResponseJsonArrayAssertionStepDefinitionsTestCaseSetup extends BaseStepDefinitionTestCaseSetup {

	protected AbstractTestService latestService;

	// unit under test
	@Override
	protected ResponseJsonArrayAssertionStepDefinitions uut () throws Exception {
		ResponseJsonArrayAssertionStepDefinitions uut = new ResponseJsonArrayAssertionStepDefinitions (
			this.serverProperties,
			this.services,
			this.testCaseIdTrackerService,
			this.jsonService,
			this.dataTableService
		);

		super.setField (uut, "restTemplate", this.restTemplate);

		latestService = createLatestServiceMock ();

		return uut;
	}

}




package com.aoede;

import com.aoede.commons.cucumber.service.AbstractTestService;
import com.aoede.commons.cucumber.stepdefs.ResponseJsonObjectAssertionStepDefinitions;

public class ResponseJsonObjectAssertionStepDefinitionsTestCaseSetup extends BaseStepDefinitionTestCaseSetup {

	protected AbstractTestService latestService;

	// unit under test
	@Override
	protected ResponseJsonObjectAssertionStepDefinitions uut () throws Exception {
		ResponseJsonObjectAssertionStepDefinitions uut = new ResponseJsonObjectAssertionStepDefinitions (
			this.serverProperties,
			this.services,
			this.testCaseIdTrackerService,
			this.jsonService,
			this.dataTableService,
			this.httpService
		);

		super.setField (uut, "restTemplate", this.restTemplate);

		latestService = createLatestServiceMock ();

		return uut;
	}

}




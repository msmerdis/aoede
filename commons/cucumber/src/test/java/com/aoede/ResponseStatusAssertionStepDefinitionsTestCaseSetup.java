package com.aoede;

import com.aoede.commons.cucumber.service.AbstractTestService;
import com.aoede.commons.cucumber.stepdefs.ResponseStatusAssertionStepDefinitions;

public class ResponseStatusAssertionStepDefinitionsTestCaseSetup extends BaseStepDefinitionTestCaseSetup {

	protected AbstractTestService latestService;

	// unit under test
	@Override
	protected ResponseStatusAssertionStepDefinitions uut () throws Exception {
		ResponseStatusAssertionStepDefinitions uut = new ResponseStatusAssertionStepDefinitions (
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




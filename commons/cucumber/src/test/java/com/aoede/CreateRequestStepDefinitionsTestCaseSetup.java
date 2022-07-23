package com.aoede;

import com.aoede.commons.cucumber.service.AbstractTestService;
import com.aoede.commons.cucumber.stepdefs.CreateRequestStepDefinitions;

public class CreateRequestStepDefinitionsTestCaseSetup extends BaseStepDefinitionTestCaseSetup {

	protected AbstractTestService latestService;

	// unit under test
	@Override
	protected CreateRequestStepDefinitions uut () throws Exception {
		CreateRequestStepDefinitions uut = new CreateRequestStepDefinitions (
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




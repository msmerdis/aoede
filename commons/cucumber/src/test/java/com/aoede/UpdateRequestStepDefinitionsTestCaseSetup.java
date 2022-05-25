package com.aoede;

import com.aoede.commons.cucumber.service.AbstractTestService;
import com.aoede.commons.cucumber.stepdefs.UpdateRequestStepDefinitions;

public class UpdateRequestStepDefinitionsTestCaseSetup extends BaseStepDefinitionTestCaseSetup {

	protected AbstractTestService latestService;

	// unit under test
	@Override
	protected UpdateRequestStepDefinitions uut () throws Exception {
		UpdateRequestStepDefinitions uut = new UpdateRequestStepDefinitions (
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




package com.aoede.commons.cucumber.stepdefs;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.CompositeIdService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.JsonObjectService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class GenericHooks extends BaseStepDefinition {

	public GenericHooks (
		ServerProperties serverProperties,
		AbstractTestServiceDiscoveryService services,
		TestCaseIdTrackerService testCaseIdTrackerService,
		CompositeIdService compositeIdService,
		JsonObjectService jsonObjectService,
		DataTableService dataTableService
	) {
		super (
			serverProperties,
			services,
			testCaseIdTrackerService,
			compositeIdService,
			jsonObjectService,
			dataTableService
		);
	}

	@Before
	@Override
	public void setup (Scenario scenario) {
		super.setup(scenario);
	}

	@After
	@Override
	public void cleanup () {
		super.cleanup();
	}

}




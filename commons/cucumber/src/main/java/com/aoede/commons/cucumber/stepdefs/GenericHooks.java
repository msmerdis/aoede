package com.aoede.commons.cucumber.stepdefs;

import com.aoede.commons.cucumber.BaseStepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class GenericHooks extends BaseStepDefinition {
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




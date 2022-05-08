package com.aoede.commons.cucumber.stepdefs;

import java.util.LinkedList;
import java.util.List;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.CompositeIdService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.JsonObjectService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
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

	private class Failure {
		public String code;
		public String text;

		public Failure (String code, String text) {
			this.code = code;
			this.text = text;
		}

		public String toString () {
			return this.code + ": " + this.text;
		}
	}

	private static List<Failure> failures = new LinkedList<Failure>();

	@Before
	@Override
	public void setup (Scenario scenario) {
		super.setup(scenario);
	}

	@After
	public void cleanup (Scenario scenario) {
		if (scenario.isFailed()) {
			failures.add(new Failure(
				super.getLatestTestCaseId(),
				scenario.getName()
			));
		}

		super.cleanup();
	}

	@AfterAll
	public static void summary () {
		System.out.println("Number of failures: " + failures.size());
		for(Failure f : failures) {
			System.out.println(f);
		}
	}

}




package com.aoede.commons.cucumber.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestTestCaseIdTrackerServiceImpl {

	// unit under test
	private TestCaseIdTrackerServiceImpl uut () throws Exception {
		return new TestCaseIdTrackerServiceImpl ();
	}

	@Test
	public void addMultipleTestCasesToService () throws Exception {
		assertTrue (
			"test case shoule be added to the service",
			uut().add("TC1", "name", "[@tag]", 1)
		);

		assertTrue (
			"test case shoule be added to the service",
			uut().add("TC2", "name", "[@tag]", 1)
		);

		assertTrue (
			"test case shoule be added to the service",
			uut().add("TC3", "name", "[@tag]", 1)
		);
	}

	@Test
	public void addScenarioOutlineToToService () throws Exception {
		assertTrue (
			"test case shoule be added to the service",
			uut().add("TC1", "name", "[@tag]", 1)
		);

		assertTrue (
			"test case shoule be added to the service",
			uut().add("TC1", "name", "[@tag]", 2)
		);

		assertTrue (
			"test case shoule be added to the service",
			uut().add("TC1", "name", "[@tag]", 3)
		);
	}

	@Test
	public void addDuplicateScenarioToToService () throws Exception {
		assertTrue (
			"test case shoule be added to the service",
			uut().add("TC1", "name1", "[@tag]", 1)
		);

		assertTrue (
			"test case shoule be added to the service",
			uut().add("TC1", "name2", "[@tag]", 2)
		);
	}

}




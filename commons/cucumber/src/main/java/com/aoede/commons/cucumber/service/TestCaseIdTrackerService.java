package com.aoede.commons.cucumber.service;

public interface TestCaseIdTrackerService {
	String getLatestTestCaseId ();
	boolean add (String testCaseId);
}




package com.aoede.commons.service;

public interface TestCaseIdTrackerService {
	String getLatestTestCaseId ();
	boolean add (String testCaseId);
}




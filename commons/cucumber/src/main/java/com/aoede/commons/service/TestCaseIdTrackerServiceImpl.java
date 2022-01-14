package com.aoede.commons.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.aoede.commons.base.BaseTestComponent;

@Service
public class TestCaseIdTrackerServiceImpl extends BaseTestComponent implements TestCaseIdTrackerService {

	private Set<String> lookup = new HashSet<String>();
	private String latestTestCaseId;

	@Override
	public boolean add (String testCaseId) {
		latestTestCaseId = testCaseId;
		return lookup.add(testCaseId);
	}

	@Override
	public String getLatestTestCaseId() {
		return latestTestCaseId;
	}
}




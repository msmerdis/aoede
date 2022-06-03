package com.aoede.commons.cucumber.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.aoede.commons.cucumber.BaseTestComponent;

@Service
public class TestCaseIdTrackerServiceImpl extends BaseTestComponent implements TestCaseIdTrackerService {

	private Set<String> lookup = new HashSet<String>();
	private String latestTestCaseId;
	private String latestName;
	private String latestTags;
	private int    latestLine;

	@Override
	public boolean add (String testCaseId, String name, String tags, int line) {
		// attempt to validate scenario as scenario outline
		// name tags and test case id should match exactly
		// but line must be increment by 1
		if (
			latestTestCaseId != null &&
			latestTestCaseId.equals(testCaseId) &&
			latestName.equals(name) &&
			latestTags.equals(tags) &&
			latestLine + 1 == line
		) {
			latestLine = line;
			return true;
		}

		// this is a new scenario
		// update all data
		latestTestCaseId = testCaseId;
		latestName = name;
		latestTags = tags;
		latestLine = line;

		return lookup.add(testCaseId);
	}

	@Override
	public String getLatestTestCaseId() {
		return latestTestCaseId;
	}
}




package com.aoede.commons.base;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import com.aoede.commons.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.service.CompositeIdService;
import com.aoede.commons.service.JsonObjectService;
import com.aoede.commons.service.TestCaseIdTrackerService;

import io.cucumber.java.Scenario;

public class ServiceStepDefinition extends BaseStepDefinition {

	@Autowired
	protected AbstractTestServiceDiscoveryService services;

	@Autowired
	private TestCaseIdTrackerService testCaseIdTrackerService;

	@Autowired
	protected CompositeIdService compositeIdService;

	@Autowired
	protected JsonObjectService jsonObjectService;

	@Override
	protected HttpHeaders defaultHeaders () {
		HttpHeaders headers = super.defaultHeaders();

		headers.set("X-Test-Case-Id", testCaseIdTrackerService.getLatestTestCaseId());

		return headers;
	}

	protected void setup (Scenario scenario) {
		services.setup();

		assertNotNull("json object service is not autowired", jsonObjectService);
		assertNotNull("composite id service is not autowired", compositeIdService);

		// verify all test cases are properly tagged
		boolean isPositive = false;
		boolean isNegative = false;
		boolean hasTCvalue = false;

		assertNotNull("testCaseIdTrackerService is not autowired", testCaseIdTrackerService);

		for (String tag : scenario.getSourceTagNames()) {
			switch (tag) {
			case "@Positive": isPositive = true; break;
			case "@Negative": isNegative = true; break;
			default:
				if (tag.startsWith("@TC")) {
					assertFalse ("test cases cannot define more than one test case id", hasTCvalue);
					assertTrue (
						"test case id's must be unique, dublicate found : " + tag,
						testCaseIdTrackerService.add(tag)
					);
					hasTCvalue = true;
				}
			}
		}

		assertTrue  ("test cases must define a test case id", hasTCvalue);
		assertTrue  ("test cases must be either @Positive  or @Negative", isPositive || isNegative);
		assertFalse ("test cases cannot be both @Positive and @Negative", isPositive && isNegative);
	}

	protected void cleanup () {
		services.clear();
		jsonObjectService.clear();
		compositeIdService.clear();
	}
}




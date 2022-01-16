package com.aoede.commons.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.http.HttpHeaders;

import com.aoede.BaseStepDefinitionTestCaseSetup;

public class TestBaseStepDefinitionDefaultHeaders extends BaseStepDefinitionTestCaseSetup {

	@Test
	public void verifyDefaultHeaders() throws Exception {
		BaseStepDefinition uut = uut ();
		String testCase = "@TC01";
		String contentType = "application/json";

		// Mock internal calls
		when(testCaseIdTrackerService.getLatestTestCaseId()).thenReturn(testCase);

		// execute function
		Method defaultHeaders = BaseStepDefinition.class.getDeclaredMethod("defaultHeaders");
		defaultHeaders.setAccessible(true);

		// assert results
		HttpHeaders headers = (HttpHeaders)defaultHeaders.invoke(uut);

		assertTrue ("headers must contain content type", headers.containsKey("Content-Type"));
		assertEquals ("content type must have a single value", 1, headers.get("Content-Type").size());
		assertTrue (headers.get("Content-Type") + " must match " + contentType, headers.get("Content-Type").contains(contentType));

		assertTrue ("headers must contain test case id", headers.containsKey("X-Test-Case-Id"));
		assertEquals ("test case id must have a single value", 1, headers.get("X-Test-Case-Id").size());
		assertTrue (headers.get("X-Test-Case-Id") + " must match " + testCase, headers.get("X-Test-Case-Id").contains(testCase));
	}

}




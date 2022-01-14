package com.aoede.commons.base;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import com.aoede.commons.service.AbstractTestService;
import com.aoede.commons.service.CompositeIdService;
import com.aoede.commons.service.JsonObjectService;
import com.aoede.commons.service.TestCaseIdTrackerService;

import io.cucumber.java.Scenario;

public class ServiceStepDefinition extends BaseStepDefinition {
	// autowired service persists between scenario execution
	@Autowired
	private ListableBeanFactory listableBeanFactory;
	private Map<String, AbstractTestService> services = new HashMap<String, AbstractTestService> ();
	private AbstractTestService latestService;

	@Autowired
	private TestCaseIdTrackerService testCaseIdTrackerService;

	@Autowired
	protected CompositeIdService compositeIdService;

	@Autowired
	protected JsonObjectService jsonObjectService;

	@PostConstruct
	private void discoverTestServices () {
		// get all abstract test service beans
		var beans = listableBeanFactory.getBeansOfType(AbstractTestService.class, false, false);
		logger.info("looking for test controller implementations in {}", this.getClass().getName());

		for (String name : beans.keySet()) {
			var service = beans.get(name);
			logger.info("identified service implementation {}", service.getClass().getName());

			if (services.containsKey(service.getName())) {
				logger.error("dublicate service with name " + service.getName() + " found");
				logger.error(" -> " + service.getClass().getName());
				logger.error(" -> " + services.get(service.getName()).getClass().getName());
				System.exit(1);
			}

			services.put(service.getName(), service);
		}
	}

	@Override
	protected HttpHeaders defaultHeaders () {
		HttpHeaders headers = super.defaultHeaders();

		headers.set("X-Test-Case-Id", testCaseIdTrackerService.getLatestTestCaseId());

		return headers;
	}

	protected AbstractTestService getService (String domain) {
		assertTrue("test controller for " + domain + " cannot be found", services.containsKey(domain));
		latestService = services.get(domain);
		return latestService;
	}

	protected AbstractTestService getService () {
		return latestService;
	}

	protected String getPath (String domain) {
		return getService(domain).getPath();
	}

	protected String getPath (String domain, String path) {
		return getPath(domain) + path;
	}

	protected void setup (Scenario scenario) {
		for (var service : services.values()) {
			service.setup ();
		}

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
		for (var service : services.values()) {
			service.cleanup ();
		}

		jsonObjectService.clear();
		compositeIdService.clear();
	}
}




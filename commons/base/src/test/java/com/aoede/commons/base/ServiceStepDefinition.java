package com.aoede.commons.base;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aoede.commons.service.AbstractTestService;

import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;

public class ServiceStepDefinition extends BaseStepDefinition {

	@Autowired
	private ListableBeanFactory listableBeanFactory;
	private Map<String, AbstractTestService> services = new HashMap<String, AbstractTestService> ();
	private AbstractTestService latestService;

	@PostConstruct
	private void discoverTestServices () {
		// get all abstract test service beans
		var beans = listableBeanFactory.getBeansOfType(AbstractTestService.class, false, false);
		logger.info("looking for test controller implementations in {}", this.getClass().getName());

		for (String name : beans.keySet()) {
			var service = beans.get(name);
			logger.info("identified service implementation {}", service.getClass().getName());

			services.put(service.getName(), service);
		}
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

	@Override
	protected void setup (TestCaseStarted event) {
		super.setup(event);

		for (var service : services.values()) {
			service.setup ();
		}
	}

	@Override
	protected void cleanup (TestCaseFinished event) {
		super.cleanup(event);

		for (var service : services.values()) {
			service.cleanup ();
		}
	}
}




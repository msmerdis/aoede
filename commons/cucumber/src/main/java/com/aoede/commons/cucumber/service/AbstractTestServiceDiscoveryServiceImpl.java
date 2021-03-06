package com.aoede.commons.cucumber.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Service;

import com.aoede.commons.cucumber.BaseTestComponent;

@Service
public class AbstractTestServiceDiscoveryServiceImpl extends BaseTestComponent implements AbstractTestServiceDiscoveryService {

	private ListableBeanFactory listableBeanFactory;
	private Map<String, AbstractTestService> services = new HashMap<String, AbstractTestService> ();

	private AbstractTestService latestService;

	public AbstractTestServiceDiscoveryServiceImpl (ListableBeanFactory listableBeanFactory) {
		this.listableBeanFactory = listableBeanFactory;
	}

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
	public AbstractTestService getService (String domain) {
		assertTrue("test controller for " + domain + " cannot be found", services.containsKey(domain));
		assertNotNull("test controller for " + domain + " is null", services.get(domain));
		return services.get(domain);
	}

	public AbstractTestService getLatestService (String domain) {
		latestService = getService(domain);
		return latestService;
	}

	public AbstractTestService getLatestService () {
		return latestService;
	}

	@Override
	public String getPathForService (String domain) {
		return getService(domain).getPath();
	}

	@Override
	public String getPathForService (String domain, String path) {
		return getPathForService(domain) + path;
	}

	@Override
	public void setup() {
		latestService = null;
		for (var service : services.values()) {
			service.setup ();
		}
	}

	@Override
	public void clear() {
		latestService = null;
		for (var service : services.values()) {
			service.clear ();
		}
	}
}




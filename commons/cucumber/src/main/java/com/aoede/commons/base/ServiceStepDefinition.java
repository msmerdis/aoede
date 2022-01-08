package com.aoede.commons.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aoede.commons.service.AbstractTestService;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;
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

			if (services.containsKey(service.getName())) {
				logger.error("dublicate service with name " + service.getName() + " found");
				logger.error(" -> " + service.getClass().getName());
				logger.error(" -> " + services.get(service.getName()).getClass().getName());
				System.exit(1);
			}

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

	protected String generateJson (DataTable data) {
		JsonObject obj = new JsonObject ();

		for (var row : data.asLists()) {
			assertEquals("Json row must have exactly three elements", 3, row.size());
			switch (row.get(1)) {
			case "int":
			case "integer":
				obj.add(row.get(0), new JsonPrimitive(Integer.parseInt(row.get(2))));
			case "long":
				obj.add(row.get(0), new JsonPrimitive(Long.parseLong(row.get(2))));
				break;
			case "number":
				obj.add(row.get(0), new JsonPrimitive(new BigInteger(row.get(2))));
				break;
			case "string":
				obj.add(row.get(0), new JsonPrimitive(row.get(2)));
				break;
			case "bool":
			case "boolean":
				obj.add(row.get(0), new JsonPrimitive(Boolean.parseBoolean(row.get(2))));
				break;
			default:
				assertFalse (true, "Type " + row.get(1) + " could not be converted to json value");
			}
		}

		return obj.toString();
	}

	protected String generateBase64 (String data) {
		return Base64.encodeBase64String(data.getBytes());
	}

	protected String generatePaddedBase64 (String data) {
		switch (data.length() % 3) {
			case 1:
				data = data + "  ";
			case 2:
				data = data + " ";
			default:
				// no padding is needed
		}
		return generateBase64 (data);
	}
}




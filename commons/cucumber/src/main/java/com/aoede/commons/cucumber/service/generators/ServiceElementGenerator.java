package com.aoede.commons.cucumber.service.generators;

import static org.junit.Assert.assertNotNull;

import com.aoede.commons.cucumber.service.AbstractTestService;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.JsonElementGenerator;
import com.google.gson.JsonElement;

public class ServiceElementGenerator implements JsonElementGenerator {
	private String type;
	private AbstractTestServiceDiscoveryService abstractTestServiceDiscoveryService;

	public ServiceElementGenerator (AbstractTestServiceDiscoveryService abstractTestServiceDiscoveryService, String type) {
		this.type = type;
		this.abstractTestServiceDiscoveryService = abstractTestServiceDiscoveryService;
	}

	@Override
	public JsonElement generate(String value) {
		AbstractTestService service = abstractTestServiceDiscoveryService.getService(value);
		assertNotNull("service for " + value + " was not found", service);

		JsonElement element = null;
		switch (type) {
		case "key"   : element = service.getLatestKey(); break;
		case "object": element = service.getLatestObj(); break;
		case "array" : element = service.getLatestArr(); break;
		}
		assertNotNull(type + " for " + value + " was not found", element);

		return element;
	}

}




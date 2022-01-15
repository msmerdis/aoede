package com.aoede.commons.test.hashmaprepository;

import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestServiceImpl;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

@Component
public class TestHashMapTestServiceImpl extends AbstractTestServiceImpl implements TestHashMapTestService {

	@Override
	public String getName() {
		return "TestHashMapDomain";
	}

	@Override
	public String getPath() {
		return "/api/test/hashmaprepository";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

	@Override
	protected void addJsonElement(JsonObject obj, String name, String value) {
		switch (name) {
		case "id":
			obj.add(name, new JsonPrimitive(Integer.parseInt(value)));
			break;
		default:
			obj.add(name, new JsonPrimitive(value));
		}
	}

}




package com.aoede.commons.test.simplekey;

import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestServiceImpl;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

@Component
public class SimpleKeyTestServiceImpl extends AbstractTestServiceImpl implements SimpleKeyTestService {

	@Override
	public String getName() {
		return "SimpleKeyDomain";
	}

	@Override
	public String getPath() {
		return "/api/test/simplekey";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

	@Override
	public void createDefaultBody(JsonObject obj) {
		obj.add("id", new JsonPrimitive(random.nextInt(200) + 40));
		obj.add("value", new JsonPrimitive(randomString(12)));
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




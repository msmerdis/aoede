package com.aoede.commons.cucumber.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.aoede.JsonServiceImplTestCaseSetup;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class TestJsonServiceImplGenerateJsonElement extends JsonServiceImplTestCaseSetup {

	@Test
	public void generateInt () throws Exception {
		JsonElement element = uut().generateJsonElement("int", "1");

		assertTrue ("element must me primitive", element.isJsonPrimitive());
		assertEquals ("element value is incorrect", "1", element.toString());
	}

	@Test
	public void generateInteger () throws Exception {
		JsonElement element = uut().generateJsonElement("int", "-123");

		assertTrue ("element must me primitive", element.isJsonPrimitive());
		assertEquals ("element value is incorrect", "-123", element.toString());
	}

	@Test
	public void generateLong () throws Exception {
		JsonElement element = uut().generateJsonElement("long", "0");

		assertTrue ("element must me primitive", element.isJsonPrimitive());
		assertEquals ("element value is incorrect", "0", element.toString());
	}

	@Test
	public void generateNumber () throws Exception {
		String number = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
		JsonElement element = uut().generateJsonElement("number", number);

		assertTrue ("element must me primitive", element.isJsonPrimitive());
		assertEquals ("element value is incorrect", number, element.toString());
	}

	@Test
	public void generateString () throws Exception {
		JsonElement element = uut().generateJsonElement("string", "Hello world!!!");

		assertTrue ("element must me primitive", element.isJsonPrimitive());
		assertEquals ("element value is incorrect", "\"Hello world!!!\"", element.toString());
	}

	@Test
	public void generateBool () throws Exception {
		JsonElement element = uut().generateJsonElement("bool", "true");

		assertTrue ("element must me primitive", element.isJsonPrimitive());
		assertEquals ("element value is incorrect", "true", element.toString());
	}

	@Test
	public void generateBoolean () throws Exception {
		JsonElement element = uut().generateJsonElement("boolean", "false");

		assertTrue ("element must me primitive", element.isJsonPrimitive());
		assertEquals ("element value is incorrect", "false", element.toString());
	}

	@Test
	public void generateCompositeId () throws Exception {
		var uut = uut ();

		uut.put("id name", new JsonPrimitive("id value"));

		JsonElement element = uut.generateJsonElement("compositeId", "id name");

		assertTrue ("element must me primitive", element.isJsonPrimitive());
		assertEquals ("element value is incorrect", "\"id value\"", element.toString());
	}

	@Test
	public void generateCompositeIdMissingId () throws Exception {
		assertThrows("invocation must fail due to an assertion", AssertionError.class, () -> {
			uut().generateJsonElement("compositeId", "id name");
		});
	}

	@Test
	public void generateKey () throws Exception {
		// mock internal calls
		setupKeyForService ("sevice name", new JsonPrimitive ("latest service key"));
		JsonElement element = uut().generateJsonElement("key", "sevice name");

		assertTrue ("element must me primitive", element.isJsonPrimitive());
		assertEquals ("element value is incorrect", "\"latest service key\"", element.toString());
	}

	@Test
	public void generateKeyMissingKey () throws Exception {
		assertThrows("invocation must fail due to an assertion", AssertionError.class, () -> {
			uut().generateJsonElement("key", "sevice name");
		});
	}

	@Test
	public void generateJson () throws Exception {
		// mock internal calls
		var uut = uut();

		uut.put("json name", new JsonObject());

		JsonElement element = uut.generateJsonElement("json", "json name");

		assertTrue ("element must me object", element.isJsonObject());
		assertEquals ("element value is incorrect", "{}", element.toString());
	}

	@Test
	public void generateJsonMissingJson () throws Exception {
		assertThrows("invocation must fail due to an assertion", AssertionError.class, () -> {
			uut().generateJsonElement("json", "json name");
		});
	}

	@Test
	public void generateNull () throws Exception {
		JsonElement element = uut().generateJsonElement("null", "anything");

		assertTrue ("element must be null", element.isJsonNull());
		assertEquals ("element value is incorrect", "null", element.toString());
	}

	@Test
	public void generateRandomString () throws Exception {
		JsonElement element = uut().generateJsonElement("random string", "anything");

		assertTrue ("element must be primitive", element.isJsonPrimitive());
		assertEquals ("element value is incorrect", "\"anything\"", element.toString());
	}

	@Test
	public void generateFraction () throws Exception {
		JsonElement element = uut().generateJsonElement("fraction", " 3 / 4 ");

		assertTrue ("element must be object", element.isJsonObject());
		assertEquals ("element value is incorrect", "{\"numerator\":3,\"denominator\":4}", element.toString());
	}

	private void setupKeyForService (String service, JsonPrimitive key) throws Exception {
		when (abstractTestServiceDiscoveryService.getService(eq(service))).thenReturn(
			new AbstractTestServiceImpl () {

				@Override
				public String getName() {
					return null;
				}

				@Override
				public String getPath() {
					return null;
				}

				@Override
				public String getKeyName() {
					return null;
				}

				@Override
				public JsonElement getLatestKey() {
					return key;
				}

			}
		);
	}
}




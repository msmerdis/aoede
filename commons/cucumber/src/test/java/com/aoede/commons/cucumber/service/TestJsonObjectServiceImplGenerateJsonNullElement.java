package com.aoede.commons.cucumber.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.aoede.JsonObjectServiceImplTestCaseSetup;
import com.google.gson.JsonElement;

public class TestJsonObjectServiceImplGenerateJsonNullElement extends JsonObjectServiceImplTestCaseSetup {

	@Test
	public void generateInt () throws Exception {
		generateInvalidNull("int");
	}

	@Test
	public void generateInteger () throws Exception {
		generateInvalidNull("integer");
	}

	@Test
	public void generateLong () throws Exception {
		generateInvalidNull("long");
	}

	@Test
	public void generateNumber () throws Exception {
		generateInvalidNull("number");
	}

	@Test
	public void generateString () throws Exception {
		// execute function
		Method setup = JsonObjectServiceImpl.class.getDeclaredMethod("generateJsonElement", String.class, String.class);
		setup.setAccessible(true);
		JsonElement element = (JsonElement) setup.invoke(uut(), "string", null);

		assertTrue ("element must me primitive", element.isJsonPrimitive());
		assertEquals ("element value is incorrect", "\"\"", element.toString());
	}

	@Test
	public void generateBool () throws Exception {
		generateInvalidNull("bool");
	}

	@Test
	public void generateBoolean () throws Exception {
		generateInvalidNull("boolean");
	}

	@Test
	public void generateCompositeId () throws Exception {
		generateInvalidNull("compositeId");
	}

	@Test
	public void generateKey () throws Exception {
		generateInvalidNull("key");
	}

	@Test
	public void generateJson () throws Exception {
		generateInvalidNull("json");
	}

	@Test
	public void generateNull () throws Exception {
		// execute function
		Method setup = JsonObjectServiceImpl.class.getDeclaredMethod("generateJsonElement", String.class, String.class);
		setup.setAccessible(true);
		JsonElement element = (JsonElement) setup.invoke(uut(), "null", null);

		assertTrue ("element must be null", element.isJsonNull());
		assertEquals ("element value is incorrect", "null", element.toString());
	}

	@Test
	public void generateRandomString () throws Exception {
		generateInvalidNull("random string");
	}

	@Test
	public void generateFraction () throws Exception {
		generateInvalidNull("fraction");
	}

	private void generateInvalidNull (String type) throws Exception {
		// execute function
		Method setup = JsonObjectServiceImpl.class.getDeclaredMethod("generateJsonElement", String.class, String.class);
		setup.setAccessible(true);

		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			setup.invoke(uut(), type, null);
		});
	}
}




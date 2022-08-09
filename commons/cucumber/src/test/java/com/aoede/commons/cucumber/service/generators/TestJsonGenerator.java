package com.aoede.commons.cucumber.service.generators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

import com.aoede.commons.cucumber.BaseTestComponent;
import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.commons.cucumber.service.JsonServiceImpl;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class TestJsonGenerator extends BaseTestComponent {

	private JsonGenerator uut (JsonService service) {
		return new JsonGenerator (service);
	}

	@Test
	public void verifyStringGeneration () {
		JsonService jsonService = Mockito.mock(JsonServiceImpl.class);
		JsonElement value = new JsonPrimitive("value");

		when(jsonService.containsKey(eq("name"))).thenReturn(true);
		when(jsonService.get(eq("name"))).thenReturn(value);

		JsonElement generated = uut(jsonService).generate("name");

		assertNotNull("generated value is null", generated);
		assertEquals("generated value is not correct", "\"value\"", generated.toString());
	}

	@Test
	public void verifyIntegerGeneration () {
		JsonService jsonService = Mockito.mock(JsonServiceImpl.class);
		JsonElement value = new JsonPrimitive(1);

		when(jsonService.containsKey(eq("name"))).thenReturn(true);
		when(jsonService.get(eq("name"))).thenReturn(value);

		JsonElement generated = uut(jsonService).generate("name");

		assertNotNull("generated value is null", generated);
		assertEquals("generated value is not correct", "1", generated.toString());
	}

	@Test
	public void verifyNullGeneration () {
		JsonService jsonService = Mockito.mock(JsonServiceImpl.class);

		when(jsonService.containsKey(eq("name"))).thenReturn(true);
		when(jsonService.get(eq("name"))).thenReturn(JsonNull.INSTANCE);

		JsonElement generated = uut(jsonService).generate("name");

		assertNotNull("generated value is null", generated);
		assertEquals("generated value is not correct", "null", generated.toString());
	}

	@Test
	public void verifyObjectGeneration() {
		JsonService jsonService = Mockito.mock(JsonServiceImpl.class);
		JsonElement value = new JsonObject();

		when(jsonService.containsKey(eq("name"))).thenReturn(true);
		when(jsonService.get(eq("name"))).thenReturn(value);

		JsonElement generated = uut(jsonService).generate("name");

		assertNotNull("generated value is null", generated);
		assertEquals("generated value is not correct", "{}", generated.toString());
	}

	@Test
	public void verifyArrayGeneration () {
		JsonService jsonService = Mockito.mock(JsonServiceImpl.class);
		JsonElement value = new JsonArray();

		when(jsonService.containsKey(eq("name"))).thenReturn(true);
		when(jsonService.get(eq("name"))).thenReturn(value);

		JsonElement generated = uut(jsonService).generate("name");

		assertNotNull("generated value is null", generated);
		assertEquals("generated value is not correct", "[]", generated.toString());
	}

	@Test
	public void verifyMissingValueFailure () {
		JsonService jsonService = Mockito.mock(JsonServiceImpl.class);

		when(jsonService.containsKey(eq("name"))).thenReturn(false);

		try {
			uut(jsonService).generate("name");
		} catch (AssertionError e) {
			assertEquals ("empty value parsed", "json name not found", e.getMessage());
			return;
		}

		assertTrue ("non existing entries cannot be used as json", false);
	}

}




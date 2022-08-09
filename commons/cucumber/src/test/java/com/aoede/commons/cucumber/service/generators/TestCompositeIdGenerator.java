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

public class TestCompositeIdGenerator extends BaseTestComponent {

	private CompositeIdGenerator uut (JsonService service) {
		return new CompositeIdGenerator (service);
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
	public void verifyIntegerGenerationFailure () {
		JsonService jsonService = Mockito.mock(JsonServiceImpl.class);
		JsonElement value = new JsonPrimitive(1);

		when(jsonService.containsKey(eq("name"))).thenReturn(true);
		when(jsonService.get(eq("name"))).thenReturn(value);

		try {
			uut(jsonService).generate("name");
		} catch (AssertionError e) {
			assertEquals ("number parsed as string", "json name is not string", e.getMessage());
			return;
		}

		assertTrue ("numbers cannot be used as composite keys", false);
	}

	@Test
	public void verifyNullGenerationFailure () {
		JsonService jsonService = Mockito.mock(JsonServiceImpl.class);

		when(jsonService.containsKey(eq("name"))).thenReturn(true);
		when(jsonService.get(eq("name"))).thenReturn(JsonNull.INSTANCE);

		try {
			uut(jsonService).generate("name");
		} catch (AssertionError e) {
			assertEquals ("null parsed as string", "json name is not primitive", e.getMessage());
			return;
		}

		assertTrue ("nulls cannot be used as composite keys", false);
	}

	@Test
	public void verifyObjectGenerationFailure () {
		JsonService jsonService = Mockito.mock(JsonServiceImpl.class);
		JsonElement value = new JsonObject();

		when(jsonService.containsKey(eq("name"))).thenReturn(true);
		when(jsonService.get(eq("name"))).thenReturn(value);

		try {
			uut(jsonService).generate("name");
		} catch (AssertionError e) {
			assertEquals ("object parsed as string", "json name is not primitive", e.getMessage());
			return;
		}

		assertTrue ("objects cannot be used as composite keys", false);
	}

	@Test
	public void verifyArrayGenerationFailure () {
		JsonService jsonService = Mockito.mock(JsonServiceImpl.class);
		JsonElement value = new JsonArray();

		when(jsonService.containsKey(eq("name"))).thenReturn(true);
		when(jsonService.get(eq("name"))).thenReturn(value);

		try {
			uut(jsonService).generate("name");
		} catch (AssertionError e) {
			assertEquals ("array parsed as string", "json name is not primitive", e.getMessage());
			return;
		}

		assertTrue ("arrays cannot be used as composite keys", false);
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

		assertTrue ("non existing entries cannot be used as composite keys", false);
	}

}




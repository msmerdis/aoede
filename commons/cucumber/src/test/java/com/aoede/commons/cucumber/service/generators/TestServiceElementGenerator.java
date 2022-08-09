package com.aoede.commons.cucumber.service.generators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

import com.aoede.commons.cucumber.BaseTestComponent;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.AbstractTestServiceImpl;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class TestServiceElementGenerator extends BaseTestComponent {

	private ServiceElementGenerator uut (AbstractTestServiceDiscoveryService service, String type) {
		return new ServiceElementGenerator (service, type);
	}

	@Test
	public void verifyKeyGeneration () {
		AbstractTestServiceDiscoveryService service = Mockito.mock(AbstractTestServiceDiscoveryService.class);

		when(service.getService(eq("name"))).thenReturn(new AbstractTestServiceImpl () {

			@Override public String getName()    { return null; }
			@Override public String getPath()    { return null; }
			@Override public String getKeyName() { return null; }

			@Override public JsonElement getLatestKey () {
				return new JsonPrimitive("key value");
			}

		});

		JsonElement generated = uut(service, "key").generate("name");

		assertNotNull("generated value is null", generated);
		assertTrue("generated value is not primitive", generated.isJsonPrimitive());
		assertTrue("generated value is not string", generated.getAsJsonPrimitive().isString());
		assertEquals("generated value is not correct", "\"key value\"", generated.toString());
	}

	@Test
	public void nullKeyFailure () {
		AbstractTestServiceDiscoveryService service = Mockito.mock(AbstractTestServiceDiscoveryService.class);

		when(service.getService(eq("name"))).thenReturn(new AbstractTestServiceImpl () {

			@Override public String getName()    { return null; }
			@Override public String getPath()    { return null; }
			@Override public String getKeyName() { return null; }

			@Override public JsonElement getLatestKey () {
				return null;
			}

		});

		try {
			uut(service, "key").generate("name");
		} catch (AssertionError e) {
			assertEquals ("non existing service cannot generate key", "key for name was not found", e.getMessage());
			return;
		}

		assertTrue ("non existing service cannot generate key", false);
	}

	@Test
	public void verifyObjectGeneration () {
		AbstractTestServiceDiscoveryService service = Mockito.mock(AbstractTestServiceDiscoveryService.class);

		when(service.getService(eq("name"))).thenReturn(new AbstractTestServiceImpl () {

			@Override public String getName()    { return null; }
			@Override public String getPath()    { return null; }
			@Override public String getKeyName() { return null; }

			@Override public JsonObject getLatestObj () {
				JsonObject object = new JsonObject();

				object.add("key", new JsonPrimitive("value"));

				return object;
			}

		});

		JsonElement generated = uut(service, "object").generate("name");

		assertNotNull("generated value is null", generated);
		assertTrue("generated value is not object", generated.isJsonObject());
		assertEquals("generated value is not correct", "{\"key\":\"value\"}", generated.toString());
	}

	@Test
	public void verifyArrayGeneration () {
		AbstractTestServiceDiscoveryService service = Mockito.mock(AbstractTestServiceDiscoveryService.class);

		when(service.getService(eq("name"))).thenReturn(new AbstractTestServiceImpl () {

			@Override public String getName()    { return null; }
			@Override public String getPath()    { return null; }
			@Override public String getKeyName() { return null; }

			@Override public JsonArray getLatestArr () {
				JsonArray array = new JsonArray();

				array.add("key");
				array.add("value");

				return array;
			}

		});

		JsonElement generated = uut(service, "array").generate("name");

		assertNotNull("generated value is null", generated);
		assertTrue("generated value is not array", generated.isJsonArray());
		assertEquals("generated value is not correct", "[\"key\",\"value\"]", generated.toString());
	}

	@Test
	public void verifyGarbGeneration () {
		AbstractTestServiceDiscoveryService service = Mockito.mock(AbstractTestServiceDiscoveryService.class);

		when(service.getService(eq("name"))).thenReturn(new AbstractTestServiceImpl () {

			@Override public String getName()    { return null; }
			@Override public String getPath()    { return null; }
			@Override public String getKeyName() { return null; }

			@Override public JsonElement getLatestKey () {
				return new JsonPrimitive("key value");
			}

		});

		try {
			uut(service, "garb").generate("name");
		} catch (AssertionError e) {
			assertEquals ("non key/object/array element cannot be generated", "garb for name was not found", e.getMessage());
			return;
		}

		assertTrue ("non existing entries cannot be used as json", false);
	}

	@Test
	public void noServiceFailure () {
		AbstractTestServiceDiscoveryService service = Mockito.mock(AbstractTestServiceDiscoveryService.class);

		when(service.getService(eq("name"))).thenReturn(null);

		try {
			uut(service, "key").generate("name");
		} catch (AssertionError e) {
			assertEquals ("non existing service cannot generate key", "service for name was not found", e.getMessage());
			return;
		}

		assertTrue ("non existing service cannot generate key", false);
	}

}




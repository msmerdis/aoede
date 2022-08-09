package com.aoede.commons.cucumber.service.generators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.aoede.commons.cucumber.BaseTestComponent;
import com.google.gson.JsonElement;

public class TestBooleanGenerator extends BaseTestComponent {

	private BooleanGenerator uut () {
		return new BooleanGenerator ();
	}

	@Test
	public void verifyTrueGeneration () {
		JsonElement generated = uut().generate("true");

		assertNotNull("generated value is null", generated);
		assertTrue("generated value is not primitive", generated.isJsonPrimitive());
		assertTrue("generated value is not boolean", generated.getAsJsonPrimitive().isBoolean());
		assertEquals("generated value is not correct", true, generated.getAsBoolean());
	}

	@Test
	public void verifyFalseGeneration () {
		JsonElement generated = uut().generate("false");

		assertNotNull("generated value is null", generated);
		assertTrue("generated value is not primitive", generated.isJsonPrimitive());
		assertTrue("generated value is not boolean", generated.getAsJsonPrimitive().isBoolean());
		assertEquals("generated value is not correct", false, generated.getAsBoolean());
	}

	@Test
	public void verifyStringGeneration () {
		JsonElement generated = uut().generate("random string turns to false");

		assertNotNull("generated value is null", generated);
		assertTrue("generated value is not primitive", generated.isJsonPrimitive());
		assertTrue("generated value is not boolean", generated.getAsJsonPrimitive().isBoolean());
		assertEquals("generated value is not correct", false, generated.getAsBoolean());
	}

}




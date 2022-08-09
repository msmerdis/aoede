package com.aoede.commons.cucumber.service.generators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.aoede.commons.cucumber.BaseTestComponent;
import com.google.gson.JsonElement;

public class TestIntegerGenerator extends BaseTestComponent {

	private IntegerGenerator uut () {
		return new IntegerGenerator ();
	}

	@Test
	public void verifyGeneration () {
		JsonElement generated = uut().generate("123");

		assertNotNull("generated value is null", generated);
		assertTrue("generated value is not primitive", generated.isJsonPrimitive());
		assertTrue("generated value is not number", generated.getAsJsonPrimitive().isNumber());
		assertEquals("generated value is not correct", "123", generated.toString());
	}

	@Test
	public void parseError () {
		assertThrows ("string cannot be parsed", NumberFormatException.class, () -> {
			uut().generate("error");
		});
	}

}




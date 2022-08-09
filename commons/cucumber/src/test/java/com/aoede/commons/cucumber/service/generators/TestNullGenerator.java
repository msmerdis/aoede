package com.aoede.commons.cucumber.service.generators;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.aoede.commons.cucumber.BaseTestComponent;
import com.google.gson.JsonElement;

public class TestNullGenerator extends BaseTestComponent {

	private NullGenerator uut () {
		return new NullGenerator ();
	}

	@Test
	public void verifyGeneration () {
		JsonElement generated = uut().generate("does not matter");

		assertNotNull("generated value is null", generated);
		assertTrue("generated value is not null", generated.isJsonNull());
	}

}




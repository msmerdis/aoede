package com.aoede.commons.cucumber.service.generators;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

import com.aoede.commons.cucumber.BaseTestComponent;

public class TestRandomStringGenerator extends BaseTestComponent {

	private RandomStringGenerator uut () {
		return new RandomStringGenerator (new Random(0));
	}
	@Test
	public void generateRandomString4 () throws Exception {
		assertEquals ("string not generated correctly", "ly4q", uut().randomString(4));
	}

	@Test
	public void generateRandomString10 () throws Exception {
		assertEquals ("string not generated correctly", "ly4qeYTuM2", uut().randomString(10));
	}

	@Test
	public void generateRandomString12 () throws Exception {
		assertEquals ("string not generated correctly", "ly4qeYTuM22G", uut().randomString(12));
	}

	@Test
	public void generateRandomStringTemplate () throws Exception {
		assertEquals ("string not generated correctly", "template_ly4qeYTu_value", uut().randomString("template_{string:8}_value"));
	}

	@Test
	public void generateRandomStringDoubleTemplate () throws Exception {
		assertEquals ("string not generated correctly", "template_ly4qeYTu_M2", uut().randomString("template_{string:8}_{string:2}"));
	}

	@Test
	public void verifyGenerate () throws Exception {
		assertEquals ("string not generated correctly", "template_ly4qeYTu_value", uut().generate("template_{string:8}_value").getAsString());
	}

}




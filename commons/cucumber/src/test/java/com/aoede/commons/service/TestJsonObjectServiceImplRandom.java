package com.aoede.commons.service;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.junit.Test;

import com.aoede.JsonObjectServiceImplTestCaseSetup;

public class TestJsonObjectServiceImplRandom extends JsonObjectServiceImplTestCaseSetup {

	@Test
	public void generateRandomString4 () throws Exception {
		// mock internal calls
		mockRandomGenerator ();

		// execute function
		Method randomString = JsonObjectServiceImpl.class.getDeclaredMethod("randomString", int.class);
		randomString.setAccessible(true);

		assertEquals ("string not generated correctly", "ly4q", randomString.invoke(uut(), 4));
	}

	@Test
	public void generateRandomString10 () throws Exception {
		// mock internal calls
		mockRandomGenerator ();

		// execute function
		Method randomString = JsonObjectServiceImpl.class.getDeclaredMethod("randomString", int.class);
		randomString.setAccessible(true);

		assertEquals ("string not generated correctly", "ly4qeYTuM2", randomString.invoke(uut(), 10));
	}

	@Test
	public void generateRandomString12 () throws Exception {
		// mock internal calls
		mockRandomGenerator ();

		// execute function
		Method randomString = JsonObjectServiceImpl.class.getDeclaredMethod("randomString", int.class);
		randomString.setAccessible(true);

		assertEquals ("string not generated correctly", "ly4qeYTuM22G", randomString.invoke(uut(), 12));
	}

	@Test
	public void generateRandomStringTemplate () throws Exception {
		// mock internal calls
		mockRandomGenerator ();

		// execute function
		Method randomString = JsonObjectServiceImpl.class.getDeclaredMethod("randomString", String.class);
		randomString.setAccessible(true);

		assertEquals ("string not generated correctly", "template_ly4qeYTu_value", randomString.invoke(uut(), "template_{string:8}_value"));
	}

	@Test
	public void generateRandomStringDoubleTemplate () throws Exception {
		// mock internal calls
		mockRandomGenerator ();

		// execute function
		Method randomString = JsonObjectServiceImpl.class.getDeclaredMethod("randomString", String.class);
		randomString.setAccessible(true);

		assertEquals ("string not generated correctly", "template_ly4qeYTu_M2", randomString.invoke(uut(), "template_{string:8}_{string:2}"));
	}

	private void mockRandomGenerator () {
		random.setSeed(0);
	}

}




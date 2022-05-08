package com.aoede.commons.cucumber.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;

import org.junit.Test;

import com.aoede.JsonObjectServiceImplTestCaseSetup;
import com.google.gson.JsonObject;

public class TestJsonObjectServiceImplBuildFraction extends JsonObjectServiceImplTestCaseSetup {

	@Test
	public void buildFraction () throws Exception {
		// execute function
		Method buildFraction = JsonServiceImpl.class.getDeclaredMethod("buildFraction", BigInteger.class, BigInteger.class);
		buildFraction.setAccessible(true);
		JsonObject object = (JsonObject) buildFraction.invoke(uut(), new BigInteger("10"), new BigInteger("20"));

		assertEquals ("fraction not generated correctly", "{\"numerator\":10,\"denominator\":20}", object.toString());
	}

	@Test
	public void buildSecondFraction () throws Exception {
		// execute function
		Method buildFraction = JsonServiceImpl.class.getDeclaredMethod("buildFraction", BigInteger.class, BigInteger.class);
		buildFraction.setAccessible(true);
		JsonObject object = (JsonObject) buildFraction.invoke(uut(), new BigInteger("1"), new BigInteger("99"));

		assertEquals ("fraction not generated correctly", "{\"numerator\":1,\"denominator\":99}", object.toString());
	}

	@Test
	public void buildFractionFromString () throws Exception {
		// execute function
		Method buildFraction = JsonServiceImpl.class.getDeclaredMethod("buildFraction", String.class);
		buildFraction.setAccessible(true);
		JsonObject object = (JsonObject) buildFraction.invoke(uut(), " 3 / 4 ");

		assertEquals ("fraction not generated correctly", "{\"numerator\":3,\"denominator\":4}", object.toString());
	}

	@Test
	public void buildFractionFromStringTwo () throws Exception {
		// execute function
		Method buildFraction = JsonServiceImpl.class.getDeclaredMethod("buildFraction", String.class);
		buildFraction.setAccessible(true);
		JsonObject object = (JsonObject) buildFraction.invoke(uut(), "1/8");

		assertEquals ("fraction not generated correctly", "{\"numerator\":1,\"denominator\":8}", object.toString());
	}

	@Test
	public void buildFractionFromStringNoDivision () throws Exception {
		// execute function
		Method buildFraction = JsonServiceImpl.class.getDeclaredMethod("buildFraction", String.class);
		buildFraction.setAccessible(true);

		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			buildFraction.invoke(uut(), "1234");
		});
	}

	@Test
	public void buildFractionFromStringMultipleParts () throws Exception {
		// execute function
		Method buildFraction = JsonServiceImpl.class.getDeclaredMethod("buildFraction", String.class);
		buildFraction.setAccessible(true);

		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			buildFraction.invoke(uut(), "1/2/3");
		});
	}

	@Test
	public void buildFractionFromStringNoNumerator () throws Exception {
		// execute function
		Method buildFraction = JsonServiceImpl.class.getDeclaredMethod("buildFraction", String.class);
		buildFraction.setAccessible(true);

		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			buildFraction.invoke(uut(), "/2");
		});
	}

	@Test
	public void buildFractionFromStringNoDenomerator () throws Exception {
		// execute function
		Method buildFraction = JsonServiceImpl.class.getDeclaredMethod("buildFraction", String.class);
		buildFraction.setAccessible(true);

		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			buildFraction.invoke(uut(), "1/");
		});
	}

}




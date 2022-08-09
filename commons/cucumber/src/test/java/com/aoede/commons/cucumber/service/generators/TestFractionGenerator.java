package com.aoede.commons.cucumber.service.generators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

import com.aoede.commons.cucumber.BaseTestComponent;
import com.google.gson.JsonElement;

public class TestFractionGenerator extends BaseTestComponent {

	private FractionGenerator uut () {
		return new FractionGenerator ();
	}

	@Test
	public void buildFraction () throws Exception {
		JsonElement generated = uut().generate(new BigInteger("10"), new BigInteger("20"));

		assertEquals ("fraction not generated correctly", "{\"numerator\":10,\"denominator\":20}", generated.toString());
	}

	@Test
	public void buildSecondFraction () throws Exception {
		JsonElement generated = uut().generate(new BigInteger("-1"), new BigInteger("99"));

		assertEquals ("fraction not generated correctly", "{\"numerator\":-1,\"denominator\":99}", generated.toString());
	}

	@Test
	public void verifyGenerationFromStringWhiteSpace () {
		JsonElement generated = uut().generate(" 3 / 4 ");

		assertEquals ("fraction not generated correctly", "{\"numerator\":3,\"denominator\":4}", generated.toString());
	}

	@Test
	public void verifyGenerationFromStringPacked () {
		JsonElement generated = uut().generate("1/8");

		assertEquals ("fraction not generated correctly", "{\"numerator\":1,\"denominator\":8}", generated.toString());
	}

	@Test
	public void verifyGenerationFromStringNoDivitionError () {
		try {
			uut().generate("1234");
		} catch (AssertionError e) {
			assertEquals ("number parsed as string", "fraction must have two parts expected:<2> but was:<1>", e.getMessage());
			return;
		}

		assertTrue ("should not generate fraction", false);
	}

	@Test
	public void verifyGenerationFromStringMultiplePartsError () throws Exception {
		try {
			uut().generate("1/2/3");
		} catch (AssertionError e) {
			assertEquals ("number parsed as string", "fraction must have two parts expected:<2> but was:<3>", e.getMessage());
			return;
		}

		assertTrue ("should not generate fraction", false);
	}

	@Test
	public void verifyGenerationFromStringNoNumeratorError () throws Exception {
		assertThrows("should not generate fraction", NumberFormatException.class, () -> {
			uut().generate("/2");
		});
	}

	@Test
	public void verifyGenerationFromStringNoDenominatorError () throws Exception {
		try {
			uut().generate("1/");
		} catch (AssertionError e) {
			assertEquals ("number parsed as string", "fraction must have two parts expected:<2> but was:<1>", e.getMessage());
			return;
		}

		assertTrue ("should not generate fraction", false);
	}
}




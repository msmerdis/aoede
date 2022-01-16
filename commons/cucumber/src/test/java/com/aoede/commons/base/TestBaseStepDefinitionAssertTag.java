package com.aoede.commons.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.aoede.BaseStepDefinitionTestCaseSetup;

public class TestBaseStepDefinitionAssertTag extends BaseStepDefinitionTestCaseSetup {

	@Test
	public void verifySuccessfulAssert() throws Exception {
		BaseStepDefinition uut = uut ();

		// execute function
		Method assertTag = BaseStepDefinition.class.getDeclaredMethod("assertTag", String.class, String.class);
		assertTag.setAccessible(true);

		// call assert function
		assertTag.invoke(uut,         "create",          "create");
		assertTag.invoke(uut,         "create trailing", "create");
		assertTag.invoke(uut, "leading create",          "create");
		assertTag.invoke(uut, "leading create trailing", "create");
	}

	@Test
	public void verifyFailedAssert() throws Exception {
		BaseStepDefinition uut = uut ();

		// execute function
		Method assertTag = BaseStepDefinition.class.getDeclaredMethod("assertTag", String.class, String.class);
		assertTag.setAccessible(true);

		// call assert function
		try {
			assertTag.invoke(uut, "anything", "create");
			assertFalse ("assertTag did not perform the assertion", true);
		} catch (InvocationTargetException e) {
			assertEquals("invocation must fail due to an assertion", "java.lang.AssertionError", e.getCause().getClass().getCanonicalName());
		}

		try {
			assertTag.invoke(uut, "reate", "create");
			assertFalse ("assertTag did not perform the assertion", true);
		} catch (InvocationTargetException e) {
			assertEquals("invocation must fail due to an assertion", "java.lang.AssertionError", e.getCause().getClass().getCanonicalName());
		}

		try {
			assertTag.invoke(uut, "creat", "create");
			assertFalse ("assertTag did not perform the assertion", true);
		} catch (InvocationTargetException e) {
			assertEquals("invocation must fail due to an assertion", "java.lang.AssertionError", e.getCause().getClass().getCanonicalName());
		}

		try {
			assertTag.invoke(uut, "crte", "create");
			assertFalse ("assertTag did not perform the assertion", true);
		} catch (InvocationTargetException e) {
			assertEquals("invocation must fail due to an assertion", "java.lang.AssertionError", e.getCause().getClass().getCanonicalName());
		}
	}

}




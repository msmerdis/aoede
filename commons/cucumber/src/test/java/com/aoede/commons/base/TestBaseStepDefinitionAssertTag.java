package com.aoede.commons.base;

import static org.junit.Assert.assertThrows;

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

		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			assertTag.invoke(uut, "anything", "create");
		});


		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			assertTag.invoke(uut, "reate", "create");
		});


		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			assertTag.invoke(uut, "creat", "create");
		});


		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			assertTag.invoke(uut, "crte", "create");
		});
	}

}




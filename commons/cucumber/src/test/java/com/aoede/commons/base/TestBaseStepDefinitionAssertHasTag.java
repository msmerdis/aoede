package com.aoede.commons.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.aoede.BaseStepDefinitionTestCaseSetup;

public class TestBaseStepDefinitionAssertHasTag extends BaseStepDefinitionTestCaseSetup {

	@Test
	public void verifySuccessfulAssert() throws Exception {
		BaseStepDefinition uut = uut ();

		// execute function
		Method assertHasTag = BaseStepDefinition.class.getDeclaredMethod("assertHasTag", String.class, String.class, Collection.class, String.class);
		assertHasTag.setAccessible(true);

		List<String> list = List.of("@Create", "@Update", "@Delete");

		// call assert function
		assertHasTag.invoke(uut,         "create",          "create", list, "@Create");
		assertHasTag.invoke(uut,         "update trailing", "update", list, "@Update");
		assertHasTag.invoke(uut, "leading delete",          "delete", list, "@Delete");
		assertHasTag.invoke(uut, "leading create trailing", "create", list, "@Create");
	}

	@Test
	public void verifyNotApplicableTests() throws Exception {
		BaseStepDefinition uut = uut ();

		// execute function
		Method assertHasTag = BaseStepDefinition.class.getDeclaredMethod("assertHasTag", String.class, String.class, Collection.class, String.class);
		assertHasTag.setAccessible(true);

		List<String> list = List.of();

		// call assert function
		assertHasTag.invoke(uut, "anything", "create", list, "@Create");
		assertHasTag.invoke(uut, "anything", "update", list, "@Update");
		assertHasTag.invoke(uut, "anything", "delete", list, "@Delete");

		assertHasTag.invoke(uut, "create", "anything", list, "@Create");
		assertHasTag.invoke(uut, "update", "anything", list, "@Update");
		assertHasTag.invoke(uut, "delete", "anything", list, "@Delete");
	}

	@Test
	public void verifyFailedAssert() throws Exception {
		BaseStepDefinition uut = uut ();

		// execute function
		Method assertHasTag = BaseStepDefinition.class.getDeclaredMethod("assertHasTag", String.class, String.class, Collection.class, String.class);
		assertHasTag.setAccessible(true);

		List<String> list = List.of();

		verifyFailedAssert_assert (assertHasTag, uut,         "create",          "create", list, "@Create");
		verifyFailedAssert_assert (assertHasTag, uut,         "update trailing", "update", list, "@Update");
		verifyFailedAssert_assert (assertHasTag, uut, "leading delete",          "delete", list, "@Delete");
		verifyFailedAssert_assert (assertHasTag, uut, "leading create trailing", "create", list, "@Create");

		list = List.of("@TC1", "@Access");

		verifyFailedAssert_assert (assertHasTag, uut,         "create",          "create", list, "@Create");
		verifyFailedAssert_assert (assertHasTag, uut,         "update trailing", "update", list, "@Update");
		verifyFailedAssert_assert (assertHasTag, uut, "leading delete",          "delete", list, "@Delete");
		verifyFailedAssert_assert (assertHasTag, uut, "leading create trailing", "create", list, "@Create");
	}

	private void verifyFailedAssert_assert (Method assertHasTag, BaseStepDefinition uut, String scenarioName, String name, Collection<String> scenarioTags, String value) throws Exception {
		try {
			assertHasTag.invoke(uut, scenarioName, name, scenarioTags, value);
			assertFalse ("assertTag did not perform the assertion", true);
		} catch (InvocationTargetException e) {
			assertEquals("invocation must fail due to an assertion", "java.lang.AssertionError", e.getCause().getClass().getCanonicalName());
		}
	}

}




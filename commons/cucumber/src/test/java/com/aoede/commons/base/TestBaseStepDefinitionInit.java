package com.aoede.commons.base;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.junit.Test;

import com.aoede.BaseStepDefinitionTestCaseSetup;

public class TestBaseStepDefinitionInit extends BaseStepDefinitionTestCaseSetup {

	@Test
	public void verifySuccessfulInit() throws Exception {
		BaseStepDefinition uut = uut ();

		// Mock internal calls
		when(serverProperties.getPort()).thenReturn(80);
		setField(uut, "basePath", "test");

		assertEquals ("basePath field was not setup correctly", "test", getField(uut, "basePath"));

		// execute function
		Method init = BaseStepDefinition.class.getDeclaredMethod("init");
		init.setAccessible(true);
		init.invoke(uut);

		assertEquals ("basePath was not updated correctly", "http://localhost:80", getField(uut, "basePath"));
	}

	@Test
	public void verifyAlternativeInit() throws Exception {
		BaseStepDefinition uut = uut ();

		// Mock internal calls
		when(serverProperties.getPort()).thenReturn(8080);
		setField(uut, "basePath", "test");

		assertEquals ("basePath field was not setup correctly", "test", getField(uut, "basePath"));

		// execute function
		Method init = BaseStepDefinition.class.getDeclaredMethod("init");
		init.setAccessible(true);
		init.invoke(uut);

		assertEquals ("basePath was not updated correctly", "http://localhost:8080", getField(uut, "basePath"));
	}
}




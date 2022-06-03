package com.aoede.commons.cucumber;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.aoede.BaseStepDefinitionTestCaseSetup;

import io.cucumber.java.Scenario;

public class TestBaseStepDefinitionEventPublishers extends BaseStepDefinitionTestCaseSetup {

	@Test
	public void setupCreateScenario() throws Exception {
		BaseStepDefinition uut = uut ();
		Scenario scenario = Mockito.mock(Scenario.class);

		// setup stubs
		doNothing().when(services).setup();
		when(scenario.getName()).thenReturn("Create new entity");
		when(scenario.getSourceTagNames()).thenReturn(List.of("@TC1", "@Create", "@Positive"));
		when(scenario.getLine()).thenReturn(1);

		when(testCaseIdTrackerService.add(eq("@TC1"),eq("create new entity"),eq("[@TC1, @Create, @Positive]"),eq(1))).thenReturn(true);

		// execute function
		Method cleanup = BaseStepDefinition.class.getDeclaredMethod("setup", Scenario.class);
		cleanup.setAccessible(true);
		cleanup.invoke(uut, scenario);

		// assert services are setup
		verify(services, times(1)).setup();
	}

	@Test
	public void setupUpdateScenario() throws Exception {
		BaseStepDefinition uut = uut ();
		Scenario scenario = Mockito.mock(Scenario.class);

		// setup stubs
		doNothing().when(services).setup();
		when(scenario.getName()).thenReturn("Update entity");
		when(scenario.getSourceTagNames()).thenReturn(List.of("@TC1", "@Update", "@Negative"));
		when(scenario.getLine()).thenReturn(1);

		when(testCaseIdTrackerService.add(eq("@TC1"),eq("update entity"),eq("[@TC1, @Update, @Negative]"),eq(1))).thenReturn(true);

		// execute function
		Method cleanup = BaseStepDefinition.class.getDeclaredMethod("setup", Scenario.class);
		cleanup.setAccessible(true);
		cleanup.invoke(uut, scenario);

		// assert services are setup
		verify(services, times(1)).setup();
	}

	@Test
	public void setupDeleteScenario() throws Exception {
		BaseStepDefinition uut = uut ();
		Scenario scenario = Mockito.mock(Scenario.class);

		// setup stubs
		doNothing().when(services).setup();
		when(scenario.getName()).thenReturn("Delete entity");
		when(scenario.getSourceTagNames()).thenReturn(List.of("@TC1", "@Delete", "@Positive"));
		when(scenario.getLine()).thenReturn(1);

		when(testCaseIdTrackerService.add(eq("@TC1"),eq("delete entity"),eq("[@TC1, @Delete, @Positive]"),eq(1))).thenReturn(true);

		// execute function
		Method cleanup = BaseStepDefinition.class.getDeclaredMethod("setup", Scenario.class);
		cleanup.setAccessible(true);
		cleanup.invoke(uut, scenario);

		// assert services are setup
		verify(services, times(1)).setup();
	}

	@Test
	public void setupDuplicateScenario() throws Exception {
		BaseStepDefinition uut = uut ();
		Scenario scenario = Mockito.mock(Scenario.class);

		// setup stubs
		doNothing().when(services).setup();
		when(scenario.getName()).thenReturn("Delete entity");
		when(scenario.getSourceTagNames()).thenReturn(List.of("@TC1", "@Delete", "@Positive"));
		when(scenario.getLine()).thenReturn(1);

		when(testCaseIdTrackerService.add(eq("@TC1"),eq("delete entity"),eq("[@TC1, @Delete, @Positive]"),eq(1))).thenReturn(false);

		// execute function
		Method cleanup = BaseStepDefinition.class.getDeclaredMethod("setup", Scenario.class);
		cleanup.setAccessible(true);

		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			cleanup.invoke(uut, scenario);
		});
	}

	@Test
	public void setupMissingTags() throws Exception {
		BaseStepDefinition uut = uut ();
		Scenario scenario = Mockito.mock(Scenario.class);

		// setup stubs
		doNothing().when(services).setup();
		when(scenario.getName()).thenReturn("something something entity");
		when(scenario.getSourceTagNames()).thenReturn(List.of("@TC1"));

		// execute function
		Method cleanup = BaseStepDefinition.class.getDeclaredMethod("setup", Scenario.class);
		cleanup.setAccessible(true);

		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			cleanup.invoke(uut, scenario);
		});
	}

	@Test
	public void setupTooManyTags() throws Exception {
		BaseStepDefinition uut = uut ();
		Scenario scenario = Mockito.mock(Scenario.class);

		// setup stubs
		doNothing().when(services).setup();
		when(scenario.getName()).thenReturn("something something entity");
		when(scenario.getSourceTagNames()).thenReturn(List.of("@TC1", "@Positive", "@Negative"));

		// execute function
		Method cleanup = BaseStepDefinition.class.getDeclaredMethod("setup", Scenario.class);
		cleanup.setAccessible(true);

		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			cleanup.invoke(uut, scenario);
		});
	}

	@Test
	public void setupCreateTag() throws Exception {
		BaseStepDefinition uut = uut ();
		Scenario scenario = Mockito.mock(Scenario.class);

		// setup stubs
		doNothing().when(services).setup();
		when(scenario.getName()).thenReturn("something something entity");
		when(scenario.getSourceTagNames()).thenReturn(List.of("@TC1", "@Negative", "@Create"));

		// execute function
		Method cleanup = BaseStepDefinition.class.getDeclaredMethod("setup", Scenario.class);
		cleanup.setAccessible(true);

		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			cleanup.invoke(uut, scenario);
		});
	}

	@Test
	public void setupUpdateTag() throws Exception {
		BaseStepDefinition uut = uut ();
		Scenario scenario = Mockito.mock(Scenario.class);

		// setup stubs
		doNothing().when(services).setup();
		when(scenario.getName()).thenReturn("something something entity");
		when(scenario.getSourceTagNames()).thenReturn(List.of("@TC1", "@Negative", "@Update"));

		// execute function
		Method cleanup = BaseStepDefinition.class.getDeclaredMethod("setup", Scenario.class);
		cleanup.setAccessible(true);

		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			cleanup.invoke(uut, scenario);
		});
	}

	@Test
	public void setupDeleteTag() throws Exception {
		BaseStepDefinition uut = uut ();
		Scenario scenario = Mockito.mock(Scenario.class);

		// setup stubs
		doNothing().when(services).setup();
		when(scenario.getName()).thenReturn("something something entity");
		when(scenario.getSourceTagNames()).thenReturn(List.of("@TC1", "@Negative", "@Delete"));

		// execute function
		Method cleanup = BaseStepDefinition.class.getDeclaredMethod("setup", Scenario.class);
		cleanup.setAccessible(true);

		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			cleanup.invoke(uut, scenario);
		});
	}

	@Test
	public void setupNoCreateTag() throws Exception {
		BaseStepDefinition uut = uut ();
		Scenario scenario = Mockito.mock(Scenario.class);

		// setup stubs
		doNothing().when(services).setup();
		when(scenario.getName()).thenReturn("Create entity");
		when(scenario.getSourceTagNames()).thenReturn(List.of("@TC1", "@Negative"));

		// execute function
		Method cleanup = BaseStepDefinition.class.getDeclaredMethod("setup", Scenario.class);
		cleanup.setAccessible(true);

		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			cleanup.invoke(uut, scenario);
		});
	}

	@Test
	public void setupNoUpdateTag() throws Exception {
		BaseStepDefinition uut = uut ();
		Scenario scenario = Mockito.mock(Scenario.class);

		// setup stubs
		doNothing().when(services).setup();
		when(scenario.getName()).thenReturn("Update entity");
		when(scenario.getSourceTagNames()).thenReturn(List.of("@TC1", "@Negative"));

		// execute function
		Method cleanup = BaseStepDefinition.class.getDeclaredMethod("setup", Scenario.class);
		cleanup.setAccessible(true);

		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			cleanup.invoke(uut, scenario);
		});
	}

	@Test
	public void setupNoDeleteTag() throws Exception {
		BaseStepDefinition uut = uut ();
		Scenario scenario = Mockito.mock(Scenario.class);

		// setup stubs
		doNothing().when(services).setup();
		when(scenario.getName()).thenReturn("Delete entity");
		when(scenario.getSourceTagNames()).thenReturn(List.of("@TC1", "@Negative"));

		// execute function
		Method cleanup = BaseStepDefinition.class.getDeclaredMethod("setup", Scenario.class);
		cleanup.setAccessible(true);

		assertThrows("invocation must fail due to an assertion", InvocationTargetException.class, () -> {
			cleanup.invoke(uut, scenario);
		});
	}

	@Test
	public void cleanup() throws Exception {
		BaseStepDefinition uut = uut ();

		// setup stubs
		doNothing().when(services).clear();
		doNothing().when(jsonService).clear();
		doNothing().when(dataTableService).clear();

		// execute function
		Method cleanup = BaseStepDefinition.class.getDeclaredMethod("cleanup");
		cleanup.setAccessible(true);
		cleanup.invoke(uut);

		// assert services are cleared
		verify(services, times(1)).clear();
		verify(jsonService, times(1)).clear();
		verify(dataTableService, times(1)).clear();
	}

}




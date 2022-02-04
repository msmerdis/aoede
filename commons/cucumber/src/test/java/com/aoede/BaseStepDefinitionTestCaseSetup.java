package com.aoede;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.BaseTestComponent;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.CompositeIdServiceImpl;
import com.aoede.commons.cucumber.service.DataTableServiceImpl;
import com.aoede.commons.cucumber.service.JsonObjectServiceImpl;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;

@RunWith(SpringRunner.class)
public class BaseStepDefinitionTestCaseSetup extends BaseTestComponent {

	@MockBean
	protected RestTemplate restTemplate;

	@MockBean
	protected ServerProperties serverProperties;

	@MockBean
	protected AbstractTestServiceDiscoveryService services;

	@MockBean
	protected TestCaseIdTrackerService testCaseIdTrackerService;

	@MockBean
	protected CompositeIdServiceImpl compositeIdService;

	@MockBean
	protected JsonObjectServiceImpl jsonObjectService;

	@MockBean
	protected DataTableServiceImpl dataTableService;

	// unit under test
	protected BaseStepDefinition uut () throws Exception {
		BaseStepDefinition uut = new BaseStepDefinition ();

		setField (uut, "restTemplate", this.restTemplate);
		setField (uut, "serverProperties", this.serverProperties);
		setField (uut, "services", this.services);
		setField (uut, "testCaseIdTrackerService", this.testCaseIdTrackerService);
		setField (uut, "jsonObjectService", this.jsonObjectService);
		setField (uut, "compositeIdService", this.compositeIdService);
		setField (uut, "dataTableService", this.dataTableService);

		return uut;
	}

	protected void setField (BaseStepDefinition uut, String fieldName, Object value) throws Exception {
		Field field = BaseStepDefinition.class.getDeclaredField(fieldName);
		field.setAccessible(true);

		assertNotNull ("field " + fieldName + " cannot be set to a null value", value);
		field.set(uut, value);
	}

	protected Object getField (BaseStepDefinition uut, String fieldName) throws Exception {
		Field field = BaseStepDefinition.class.getDeclaredField(fieldName);

		field.setAccessible(true);

		return field.get(uut);
	}

}




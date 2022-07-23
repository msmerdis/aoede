package com.aoede;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.BaseTestComponent;
import com.aoede.commons.cucumber.ResponseResults;
import com.aoede.commons.cucumber.service.AbstractTestService;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.HttpService;
import com.aoede.commons.cucumber.service.HttpServiceImpl;
import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;

@RunWith(SpringRunner.class)
public class BaseStepDefinitionTestCaseSetup extends BaseTestComponent {

	@MockBean protected RestTemplate restTemplate;
	@MockBean protected ServerProperties serverProperties;
	@MockBean protected AbstractTestServiceDiscoveryService services;
	@MockBean protected TestCaseIdTrackerService testCaseIdTrackerService;
	@MockBean protected JsonService jsonService;
	@MockBean protected DataTableService dataTableService;

	protected HttpService httpService = new HttpServiceImpl();

	// unit under test
	protected BaseStepDefinition uut () throws Exception {
		BaseStepDefinition uut = new BaseStepDefinition (
			this.serverProperties,
			this.services,
			this.testCaseIdTrackerService,
			this.jsonService,
			this.dataTableService,
			this.httpService
		);

		setField (uut, "restTemplate", this.restTemplate);

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

	protected ResponseResults stubRequestCall(BaseStepDefinition uut, AbstractTestService service, HttpStatus status, HttpMethod method, String body, String path) throws Exception {

		ResponseResults results = new ResponseResults ();
		results.body = body;
		results.headers = new HttpHeaders ();
		results.status = status;

		// Mock internal calls
		setField (uut, "basePath", "https://localhost");
		when(restTemplate.execute(eq("https://localhost/the/path"), eq(method), any(), any())).thenReturn(results);
		when(services.getService(eq("domain"))).thenReturn(service);
		when(services.getPathForService(eq("domain"))).thenReturn("/the/path");
		when(services.getPathForService(eq("domain"), eq(path))).thenReturn("/the/path");

		return results;
	}

}




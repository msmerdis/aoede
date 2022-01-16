package com.aoede.commons.base;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import com.aoede.commons.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.service.CompositeIdService;
import com.aoede.commons.service.DataTableService;
import com.aoede.commons.service.JsonObjectService;
import com.aoede.commons.service.TestCaseIdTrackerService;

import io.cucumber.java.Scenario;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;

public class BaseStepDefinition extends BaseTestComponent implements EventListener {
	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private ServerProperties serverProperties;
	private String basePath;

	@Autowired
	protected AbstractTestServiceDiscoveryService services;

	@Autowired
	private TestCaseIdTrackerService testCaseIdTrackerService;

	@Autowired
	protected CompositeIdService compositeIdService;

	@Autowired
	protected JsonObjectService jsonObjectService;

	@Autowired
	protected DataTableService dataTableService;

	@PostConstruct
	void init () {
		basePath = "http://localhost:" + serverProperties.getPort();
	}

	private class RequestParametersCallback implements RequestCallback {
		public HttpHeaders requestHeaders;
		public String      requestBody;

		public RequestParametersCallback (HttpHeaders headers, String body) {
			this.requestHeaders = headers;
			this.requestBody    = body;

			if (this.requestHeaders.getAccept().size() == 0) {
				this.requestHeaders.setAccept(defaultAccept());
			}
		}

		private List<MediaType> defaultAccept () {
			List<MediaType> accept = new LinkedList<MediaType> ();

			accept.add(MediaType.APPLICATION_JSON);

			return accept;
		}

		@Override
		public void doWithRequest(ClientHttpRequest request) throws IOException {
			// setup headers for the request
			if (requestHeaders != null) {
				request.getHeaders().addAll(requestHeaders);
			}

			// setup a body for the request
			if (requestBody != null) {
				request.getBody().write(requestBody.getBytes());
			}
		}
	}

	private ResponseResults execute (String url, HttpMethod method, HttpHeaders headers, String body) {
		ResponseResults response = new ResponseResults ();

		logger.info ("----------------------------------------------------------------");
		logger.info ("- Request:  " + method + " " + basePath + url);
		logger.info ("Headers:");
		for (var header : headers.entrySet())
			logger.info (" - " + header.getKey() + ": " + header.getValue());
		logger.info ("body:");
		logger.info (body);

		restTemplate.setErrorHandler(response);
		restTemplate.execute(basePath + url, method, new RequestParametersCallback(headers, body), r -> {
			return response;
		});

		logger.info ("----------------------------------------------------------------");
		logger.info ("- Response: " + response.status.toString());
		logger.info ("Headers:");
		for (var header : response.headers.entrySet())
			logger.info (" - " + header.getKey() + ": " + header.getValue());
		logger.info ("body:");
		logger.info (response.body);

		return response;
	}

	protected ResponseResults executeGet (String path, HttpHeaders headers) {
		return execute (path, HttpMethod.GET, headers, null);
	}

	protected ResponseResults executePost (String path, HttpHeaders headers, String body) {
		return execute (path, HttpMethod.POST, headers, body);
	}

	protected ResponseResults executePut (String path, HttpHeaders headers, String body) {
		return execute (path, HttpMethod.PUT, headers, body);
	}

	protected ResponseResults executeDelete (String path, HttpHeaders headers) {
		return execute (path, HttpMethod.DELETE, headers, null);
	}

	protected ResponseResults executeGet (String path) {
		return executeGet (path, defaultHeaders());
	}

	protected ResponseResults executePost (String path, String body) {
		return executePost (path, defaultHeaders(), body);
	}

	protected ResponseResults executePut (String path, String body) {
		return executePut (path, defaultHeaders(), body);
	}

	protected ResponseResults executeDelete (String path) {
		return executeDelete (path, defaultHeaders());
	}

	protected HttpHeaders defaultHeaders () {
		HttpHeaders headers = new HttpHeaders ();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-Test-Case-Id", testCaseIdTrackerService.getLatestTestCaseId());

		return headers;
	}

	@Override
	public void setEventPublisher(EventPublisher publisher) {}

	protected void setup (Scenario scenario) {
		services.setup();

		assertNotNull("json object service is not autowired", jsonObjectService);
		assertNotNull("composite id service is not autowired", compositeIdService);

		// verify all test cases are properly tagged
		boolean isPositive = false;
		boolean isNegative = false;
		boolean hasTCvalue = false;

		assertNotNull("testCaseIdTrackerService is not autowired", testCaseIdTrackerService);

		String scenarioName = scenario.getName().toLowerCase();
		Collection<String> scenarioTags = scenario.getSourceTagNames();

		for (String tag : scenarioTags) {
			switch (tag) {
			case "@Positive": isPositive = true; break;
			case "@Negative": isNegative = true; break;
			case "@Create": assertTag(scenarioName, "create"); break;
			case "@Update": assertTag(scenarioName, "update"); break;
			case "@Delete": assertTag(scenarioName, "delete"); break;
			default:
				if (tag.startsWith("@TC")) {
					assertFalse ("test cases cannot define more than one test case id", hasTCvalue);
					assertTrue (
						"test case id's must be unique, dublicate found : " + tag,
						testCaseIdTrackerService.add(tag)
					);
					hasTCvalue = true;
				}
			}
		}

		assertHasTag (scenarioName, "create ", scenarioTags, "@Create");
		assertHasTag (scenarioName, "update ", scenarioTags, "@Update");
		assertHasTag (scenarioName, "delete ", scenarioTags, "@Delete");

		assertTrue  ("test cases must define a test case id", hasTCvalue);
		assertTrue  ("test cases must be either @Positive  or @Negative", isPositive || isNegative);
		assertFalse ("test cases cannot be both @Positive and @Negative", isPositive && isNegative);
	}

	private void assertTag (String name, String value) {
		assertTrue ("scenario does not contain keyword " + value, name.contains(value));
	}

	private void assertHasTag (String scenarioName, String name, Collection<String> scenarioTags, String value) {
		if (scenarioName.contains(name))
			assertTrue ("scenario is not tagged with " + value, scenarioTags.contains(value));
	}

	protected void cleanup () {
		services.clear();
		jsonObjectService.clear();
		compositeIdService.clear();
		dataTableService.clear();
	}
}




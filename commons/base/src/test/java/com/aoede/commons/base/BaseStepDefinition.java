package com.aoede.commons.base;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.aoede.commons.base.component.BaseComponent;

import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;

public class BaseStepDefinition extends BaseComponent implements EventListener {
	static ResponseResults latestResponse = new ResponseResults ();

	protected RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private ServerProperties serverProperties;
	private String basePath;

	@PostConstruct
	void init () {
		restTemplate.setErrorHandler(latestResponse);
		basePath = "http://localhost:" + serverProperties.getPort();
	}

	private static class ResponseResults implements ResponseErrorHandler {
		public HttpHeaders headers = null;
		public HttpStatus  status  = null;
		public String      body    = null;

		@Override
		public boolean hasError(ClientHttpResponse response) throws IOException {
			this.headers = response.getHeaders();
			this.status  = response.getStatusCode();
			this.body    = new String (response.getBody().readAllBytes());
			return false;
		}

		@Override
		public void handleError(ClientHttpResponse response) throws IOException {
		}

		public void cleanup () {
			this.headers = null;
			this.status  = null;
			this.body    = null;
		}
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

	private void execute (String url, HttpMethod method, HttpHeaders headers, String body) {
		logger.info ("----------------------------------------------------------------");
		logger.info ("- Request");
		logger.info (" -> " + method + " " + basePath + url);
		logger.info (" -> " + body);

		restTemplate.execute(basePath + url, method, new RequestParametersCallback(headers, body), r -> {
			return latestResponse;
		});

		logger.info ("----------------------------------------------------------------");
		logger.info ("- Response");
		logger.info (" -> " + latestResponse.status.toString());
		logger.info (" -> " + latestResponse.body);
	}

	protected void executeGet (String path, HttpHeaders headers) {
		execute (path, HttpMethod.GET, headers, null);
	}

	protected void executePost (String path, HttpHeaders headers, String body) {
		execute (path, HttpMethod.POST, headers, body);
	}

	protected void executePut (String path, HttpHeaders headers, String body) {
		execute (path, HttpMethod.PUT, headers, body);
	}

	protected void executeDelete (String path, HttpHeaders headers) {
		execute (path, HttpMethod.DELETE, headers, null);
	}

	protected void executeGet (String path) {
		executeGet (path, defaultHeaders());
	}

	protected void executePost (String path, String body) {
		executePost (path, defaultHeaders(), body);
	}

	protected void executePut (String path, String body) {
		executePut (path, defaultHeaders(), body);
	}

	protected void executeDelete (String path) {
		executeDelete (path, defaultHeaders());
	}

	protected HttpHeaders defaultHeaders () {
		HttpHeaders headers = new HttpHeaders ();

		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	protected HttpStatus getResponseStatus () {
		return latestResponse.status;
	}

	protected HttpHeaders getResponseHeaders () {
		return latestResponse.headers;
	}

	protected String getResponseBody () {
		return latestResponse.body;
	}

	@Override
	public void setEventPublisher(EventPublisher publisher) {
		publisher.registerHandlerFor(TestCaseStarted.class, event -> {
			this.setup(event);
		});

		publisher.registerHandlerFor(TestCaseFinished.class, event -> {
			this.cleanup(event);
		});
	}

	protected void setup (TestCaseStarted event) {
	}

	protected void cleanup (TestCaseFinished event) {
		latestResponse.cleanup();
	}
}




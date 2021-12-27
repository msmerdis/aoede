package com.aoede.commons.base;

import java.io.IOException;
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

import com.aoede.commons.base.component.BaseComponent;

import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;

public class BaseStepDefinition extends BaseComponent implements EventListener {
	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private ServerProperties serverProperties;
	private String basePath;

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
		logger.info ("- Request");
		logger.info (" -> " + method + " " + basePath + url);
		logger.info (" -> " + body);

		restTemplate.setErrorHandler(response);
		restTemplate.execute(basePath + url, method, new RequestParametersCallback(headers, body), r -> {
			return response;
		});

		logger.info ("----------------------------------------------------------------");
		logger.info ("- Response");
		logger.info (" -> " + response.status.toString());
		logger.info (" -> " + response.body);

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
		return headers;
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
	}
}




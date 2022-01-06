package com.aoede.commons.base.client;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;

public class AbstractClientRequestCallback implements RequestCallback {
	private HttpHeaders requestHeaders;
	private String      requestBody;

	final public static List<MediaType> DEFAULT_ACCEPT_HEADERS = List.of(
		MediaType.APPLICATION_JSON
	);

	public AbstractClientRequestCallback (HttpHeaders headers) {
		init (headers, null);
	}

	public AbstractClientRequestCallback (HttpHeaders headers, String body) {
		init (headers, body);
	}

	private void init (HttpHeaders headers, String body) {
		this.requestHeaders = headers;
		this.requestBody    = body;

		if (this.requestHeaders.getAccept().size() == 0) {
			this.requestHeaders.setAccept(DEFAULT_ACCEPT_HEADERS);
		}
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




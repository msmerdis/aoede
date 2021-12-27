package com.aoede.commons.base;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class ResponseResults implements ResponseErrorHandler {
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
}




package com.aoede.commons.base.client;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class AbstractClientResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		switch (response.getStatusCode()) {
			case OK:
			case CREATED:
			case NO_CONTENT:
				return true;
			default:
				return false;
		}
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
	}
}




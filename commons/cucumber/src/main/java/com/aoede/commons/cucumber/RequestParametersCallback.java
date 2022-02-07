package com.aoede.commons.cucumber;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;

import com.aoede.commons.cucumber.addon.RequestParameterAddon;

public class RequestParametersCallback implements RequestCallback {
	private static Set<RequestParameterAddon> addons = new HashSet<RequestParameterAddon>();

	public static void register (RequestParameterAddon addon) {
		if (addons.contains(addon) == false) {
			addons.add(addon);
		}
	}

	public static void unregister (RequestParameterAddon addon) {
		if (addons.contains(addon)) {
			addons.remove(addon);
		}
	}

	public static boolean isRegistered (RequestParameterAddon addon) {
		return addons.contains(addon);
	}

	public HttpHeaders requestHeaders;
	public String      requestBody;

	public RequestParametersCallback (HttpHeaders headers, String body) {
		this.requestHeaders = headers;
		this.requestBody    = body;

		// configure each request add on
		addons.forEach(addon -> addon.configure(headers));

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




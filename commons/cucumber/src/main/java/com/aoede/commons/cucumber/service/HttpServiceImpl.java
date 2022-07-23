package com.aoede.commons.cucumber.service;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Service
public class HttpServiceImpl implements HttpService {
	private HttpHeaders headers;

	@Setter
	private String url;

	public HttpServiceImpl () {
		clear();
	}

	@Override
	public void clear() {
		headers = new HttpHeaders();
		url     = null;
	}

}




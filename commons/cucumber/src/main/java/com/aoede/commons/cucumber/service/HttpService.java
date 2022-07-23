package com.aoede.commons.cucumber.service;

import org.springframework.util.MultiValueMap;

public interface HttpService {
	MultiValueMap<String, String> getHeaders ();

	String getUrl();
	void   setUrl(String url);

	void clear ();
}




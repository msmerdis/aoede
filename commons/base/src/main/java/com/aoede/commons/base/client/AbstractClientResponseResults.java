package com.aoede.commons.base.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class AbstractClientResponseResults {
	public HttpHeaders headers = null;
	public HttpStatus  status  = null;
	public String      body    = null;
}




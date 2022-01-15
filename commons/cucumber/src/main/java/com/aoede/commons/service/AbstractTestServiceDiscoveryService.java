package com.aoede.commons.service;

public interface AbstractTestServiceDiscoveryService {

	public AbstractTestService getService (String domain);

	public String getPathForService (String domain);
	public String getPathForService (String domain, String path);

	public void setup();
	public void clear();
}




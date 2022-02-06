package com.aoede.commons.cucumber.service;

public interface AbstractTestServiceDiscoveryService {

	public AbstractTestService getService (String domain);

	public AbstractTestService getLatestService (String domain);
	public AbstractTestService getLatestService ();


	public String getPathForService (String domain);
	public String getPathForService (String domain, String path);

	public void setup();
	public void clear();
}




package com.aoede.service;

import com.aoede.commons.base.ResponseResults;
import com.google.gson.JsonObject;

public interface LoginTestService {
	public void results (ResponseResults results);

	public boolean isSuccess();
	public String getLoginToken();
	public ResponseResults getLatestResults();
	public JsonObject getUserDetails();
}




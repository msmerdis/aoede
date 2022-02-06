package com.aoede.modules.user.service;

import com.aoede.commons.cucumber.ResponseResults;
import com.aoede.commons.cucumber.addon.RequestParameterAddon;
import com.google.gson.JsonObject;

public interface LoginTestService extends RequestParameterAddon {
	public void results (ResponseResults results);

	public boolean isSuccess();
	public String getLoginToken();
	public ResponseResults getLatestResults();
	public JsonObject getUserDetails();
}




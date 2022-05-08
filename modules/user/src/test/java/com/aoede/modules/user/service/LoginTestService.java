package com.aoede.modules.user.service;

import com.aoede.commons.cucumber.ResponseResults;
import com.aoede.commons.cucumber.addon.RequestParameterAddon;
import com.aoede.commons.cucumber.service.AbstractTestService;

public interface LoginTestService extends AbstractTestService, RequestParameterAddon {
	public String getLoginToken();
	public void loginResults (ResponseResults results);
}




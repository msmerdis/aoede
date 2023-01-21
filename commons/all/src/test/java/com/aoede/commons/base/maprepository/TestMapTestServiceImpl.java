package com.aoede.commons.base.maprepository;

import org.springframework.stereotype.Component;

import com.aoede.commons.cucumber.service.AbstractTestServiceImpl;

@Component
public class TestMapTestServiceImpl extends AbstractTestServiceImpl implements TestMapTestService {

	@Override
	public String getName() {
		return "TestMapDomain";
	}

	@Override
	public String getPath() {
		return "/api/test/maprepository";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

}




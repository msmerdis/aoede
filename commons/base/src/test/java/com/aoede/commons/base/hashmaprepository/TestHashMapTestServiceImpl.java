package com.aoede.commons.base.hashmaprepository;

import org.springframework.stereotype.Component;

import com.aoede.commons.cucumber.service.AbstractTestServiceImpl;

@Component
public class TestHashMapTestServiceImpl extends AbstractTestServiceImpl implements TestHashMapTestService {

	@Override
	public String getName() {
		return "TestHashMapDomain";
	}

	@Override
	public String getPath() {
		return "/api/test/hashmaprepository";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

}




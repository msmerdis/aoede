package com.aoede.commons.test.hashmaprepository;

import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestServiceImpl;

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




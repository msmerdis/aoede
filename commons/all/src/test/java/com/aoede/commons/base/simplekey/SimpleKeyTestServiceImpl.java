package com.aoede.commons.base.simplekey;

import org.springframework.stereotype.Component;

import com.aoede.commons.cucumber.service.AbstractTestServiceImpl;

@Component
public class SimpleKeyTestServiceImpl extends AbstractTestServiceImpl implements SimpleKeyTestService {

	@Override
	public String getName() {
		return "SimpleKeyDomain";
	}

	@Override
	public String getPath() {
		return "/api/test/simplekey";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

}




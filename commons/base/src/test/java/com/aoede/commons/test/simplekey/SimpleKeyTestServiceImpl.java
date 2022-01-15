package com.aoede.commons.test.simplekey;

import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestServiceImpl;

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




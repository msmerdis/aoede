package com.aoede.commons.base.compositekey;

import org.springframework.stereotype.Component;

import com.aoede.commons.cucumber.service.AbstractTestServiceImpl;

@Component
public class CompositeKeyTestServiceImpl extends AbstractTestServiceImpl implements CompositeKeyTestService {

	@Override
	public String getName() {
		return "CompositeKeyDomain";
	}

	@Override
	public String getPath() {
		return "/api/test/compositekey";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

}




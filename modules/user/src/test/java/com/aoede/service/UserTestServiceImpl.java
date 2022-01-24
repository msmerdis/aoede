package com.aoede.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestServiceImpl;

@Component
public class UserTestServiceImpl extends AbstractTestServiceImpl implements RoleTestService {

	@Override
	public String getName() {
		return "user";
	}

	@Override
	public String getPath() {
		return "/test/user";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

}




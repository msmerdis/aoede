package com.aoede.modules.user.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.cucumber.service.AbstractTestServiceImpl;

@Component
public class UserTestServiceImpl extends AbstractTestServiceImpl implements RoleTestService {

	@Override
	public String getName() {
		return "user";
	}

	@Override
	public String getPath() {
		return "/admin/user";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

}




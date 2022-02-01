package com.aoede.service;

import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestServiceImpl;

@Component
public class RoleTestServiceImpl extends AbstractTestServiceImpl implements RoleTestService {

	@Override
	public String getName() {
		return "role";
	}

	@Override
	public String getPath() {
		return "/admin/user/role";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

}




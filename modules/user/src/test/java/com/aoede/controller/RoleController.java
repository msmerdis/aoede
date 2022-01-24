package com.aoede.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractDomainController;
import com.aoede.modules.user.domain.Role;
import com.aoede.modules.user.service.RoleService;

@RestController
@RequestMapping ("/test/role")
public class RoleController extends AbstractDomainController<Integer, Role, RoleService> {

	public RoleController(RoleService service) {
		super(service);
	}

}




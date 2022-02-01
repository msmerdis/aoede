package com.aoede.modules.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractDomainController;
import com.aoede.modules.user.domain.Role;
import com.aoede.modules.user.service.RoleService;

@RestController
@RequestMapping ("/admin/user/role")
public class RoleAdminController extends AbstractDomainController<Integer, Role, RoleService> {

	public RoleAdminController(RoleService service) {
		super(service);
	}

}




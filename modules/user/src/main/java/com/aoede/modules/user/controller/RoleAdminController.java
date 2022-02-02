package com.aoede.modules.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractResourceController;
import com.aoede.modules.user.domain.Role;
import com.aoede.modules.user.service.RoleService;
import com.aoede.modules.user.transfer.role.CreateRole;
import com.aoede.modules.user.transfer.role.DetailRoleResponse;
import com.aoede.modules.user.transfer.role.SimpleRoleResponse;
import com.aoede.modules.user.transfer.role.UpdateRole;

@RestController
@RequestMapping ("/admin/user/role")
public class RoleAdminController extends AbstractResourceController<
	Integer,
	Role,
	Integer,
	CreateRole,
	UpdateRole,
	SimpleRoleResponse,
	DetailRoleResponse,
	RoleService
> {

	public RoleAdminController(RoleService service) {
		super(service);
	}

	@Override
	public SimpleRoleResponse simpleResponse(Role entity, boolean includeParent, boolean cascade) {
		return detailResponse(entity, includeParent, cascade);
	}

	@Override
	public DetailRoleResponse detailResponse(Role entity, boolean includeParent, boolean cascade) {
		DetailRoleResponse response = new DetailRoleResponse ();

		response.setId(entity.getId());
		response.setRole(entity.getRole());
		response.setDesc(entity.getDesc());

		return response;
	}

	@Override
	public Role createDomain(CreateRole data) {
		return updateDomain(data);
	}

	@Override
	public Role updateDomain(UpdateRole data) {
		Role domain = new Role();

		domain.setRole(data.getRole());
		domain.setDesc(data.getDesc());

		return domain;
	}

	@Override
	public Integer createDomainKey(Integer data) {
		return data;
	}

}




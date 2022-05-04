package com.aoede.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.modules.user.domain.Role;
import com.aoede.modules.user.domain.User;
import com.aoede.modules.user.service.RoleService;
import com.aoede.modules.user.service.UserService;
import com.aoede.modules.user.transfer.user.UserStatus;

@Component
public class GenerateUsersRunner extends BaseComponent implements CommandLineRunner {
	private RoleService roleService;
	private UserService userService;

	public GenerateUsersRunner (RoleService roleService, UserService userService) {
		this.roleService = roleService;
		this.userService = userService;
	}

	@Override
	public void run(String... args) throws Exception {
		generateRoles();
		generateUsers();
	}

	private Role makeRole(String role, String desc) {
		Role rtn = new Role();

		rtn.setRole(role);
		rtn.setDesc(desc);

		return rtn;
	}

	private User makeUser(String username, String password) {
		User rtn = new User();

		rtn.setStatus(UserStatus.ACTIVE);
		rtn.setUsername(username);
		rtn.setPassword(password);

		return rtn;
	}

	private void generateRoles () throws Exception {
		roleService.create(makeRole("pleb", "just a pleb, nothing to see here"));
		roleService.create(makeRole("boss", "how we talking"));
	}

	private void generateUsers () throws Exception {
		userService.create(makeUser("pleb", "test"));
		userService.create(makeUser("boss", "test"));
	}

}




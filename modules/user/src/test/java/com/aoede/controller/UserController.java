package com.aoede.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractDomainController;
import com.aoede.modules.user.domain.User;
import com.aoede.modules.user.service.UserService;

@RestController
@RequestMapping ("/test/user")
public class UserController extends AbstractDomainController<Long, User, UserService> {

	public UserController(UserService service) {
		super(service);
	}

}




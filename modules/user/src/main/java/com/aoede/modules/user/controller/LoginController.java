package com.aoede.modules.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void login () {
	}

	@GetMapping("/logout")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void logout () {
	}
}




package com.aoede.modules.user.controller;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.modules.user.config.UserConfiguration;
import com.aoede.modules.user.domain.User;
import com.aoede.modules.user.service.TokenService;
import com.aoede.modules.user.service.UserService;
import com.aoede.modules.user.transfer.user.LoginRequest;
import com.aoede.modules.user.transfer.user.UserDetailResponse;

@RestController
@RequestMapping("/login")
public class LoginController extends BaseComponent {

	private UserService  userService;
	private TokenService tokenService;

	public LoginController(UserService userService, TokenService tokenService) {
		this.userService  = userService;
		this.tokenService = tokenService;
	}

	@PostMapping
	public ResponseEntity<UserDetailResponse> login (@Valid @RequestBody LoginRequest loginDetails) throws GenericException {
		return responseEntity(
			userService.login(loginDetails.getUsername(), loginDetails.getPassword()));
	}

	@GetMapping
	public ResponseEntity<UserDetailResponse> status (@RequestHeader(UserConfiguration.TOKEN_HEADER_NAME) String token) throws GenericException {
		return responseEntity(
			userService.find(
				tokenService.decodeUser(token).getId()));
	}

	private ResponseEntity<UserDetailResponse> responseEntity (User user) {
		UserDetailResponse userDetails = new UserDetailResponse();

		userDetails.setStatus(user.getStatus().toString());
		userDetails.setUsername(user.getUsername());

		HttpHeaders headers = new HttpHeaders ();

		headers.add(
			UserConfiguration.TOKEN_HEADER_NAME,
			tokenService.encodeUser(user, UserConfiguration.TOKEN_TIME_TO_LIVE)
		);

		return new ResponseEntity<UserDetailResponse>(userDetails, headers, HttpStatus.OK);
	}

}




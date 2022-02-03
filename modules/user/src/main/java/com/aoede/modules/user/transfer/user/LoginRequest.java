package com.aoede.modules.user.transfer.user;

import javax.validation.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LoginRequest {
	@NotEmpty(message = "Username is mandatory")
	private String username;

	@NotEmpty(message = "Password is mandatory")
	private String password;
}




package com.aoede.modules.user.transfer.user;

import javax.validation.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class CreateUser extends UpdateUser {
	@NotEmpty(message = "User must define a password")
	private String password;
}




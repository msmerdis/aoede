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
public class UpdateUser {
	@NotEmpty(message = "User must define a user status")
	private String status;

	@NotEmpty(message = "User must define a username")
	private String username;
}




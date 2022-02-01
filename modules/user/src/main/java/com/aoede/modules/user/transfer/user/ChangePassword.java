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
public class ChangePassword {
	@NotEmpty(message = "Password cannot be empty")
	private String password;
}




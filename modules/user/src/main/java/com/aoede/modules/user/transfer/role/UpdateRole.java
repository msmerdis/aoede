package com.aoede.modules.user.transfer.role;

import javax.validation.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UpdateRole {
	@NotEmpty(message = "Role must define a name")
	private String role;

	@NotEmpty(message = "Role must define a description")
	private String desc;
}




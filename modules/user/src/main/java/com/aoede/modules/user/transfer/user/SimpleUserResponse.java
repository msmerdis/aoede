package com.aoede.modules.user.transfer.user;

import java.util.HashSet;
import java.util.Set;

import com.aoede.commons.base.transfer.AbstractResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SimpleUserResponse extends UpdateUser implements AbstractResponse <Long> {
	private Long id;
	private String status;
	private String username;
	private Set<String> roles = new HashSet<String>();
}




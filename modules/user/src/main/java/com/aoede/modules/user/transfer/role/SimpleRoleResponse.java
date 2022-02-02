package com.aoede.modules.user.transfer.role;

import com.aoede.commons.base.transfer.AbstractResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SimpleRoleResponse extends UpdateRole implements AbstractResponse <Integer> {
	private Integer id;
}




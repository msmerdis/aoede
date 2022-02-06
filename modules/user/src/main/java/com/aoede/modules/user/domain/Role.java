package com.aoede.modules.user.domain;

import org.springframework.security.core.GrantedAuthority;

import com.aoede.commons.base.domain.AbstractDomain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Role implements AbstractDomain<Integer>, GrantedAuthority {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String role;
	private String desc;

	@Override
	public String getAuthority() {
		return role;
	}

}




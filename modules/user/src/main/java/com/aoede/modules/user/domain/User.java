package com.aoede.modules.user.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.aoede.commons.base.domain.AbstractDomain;
import com.aoede.modules.user.transfer.user.UserStatus;

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
public class User implements UserDetails, AbstractDomain<Long>  {
	private static final long serialVersionUID = 1L;

	private Long id;
	private UserStatus status;
	private String username;
	private String password;
	private Set<SimpleGrantedAuthority> roles = new HashSet<SimpleGrantedAuthority>();

	@Override
	public boolean isAccountNonExpired() {
		return status != UserStatus.SUSPENDED;
	}

	@Override
	public boolean isAccountNonLocked() {
		return status != UserStatus.LOCKED;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return status != UserStatus.LOCKED;
	}

	@Override
	public boolean isEnabled() {
		return status == UserStatus.ACTIVE;
	}

	@Override
	public Set<SimpleGrantedAuthority> getAuthorities() {
		return roles;
	}

}




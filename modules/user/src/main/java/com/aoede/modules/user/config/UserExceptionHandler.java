package com.aoede.modules.user.config;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.exceptions.UnauthorizedException;

@RestControllerAdvice
public class UserExceptionHandler extends BaseComponent {

	@ExceptionHandler(UsernameNotFoundException.class)
	public final ResponseEntity<?> handleUsernameNotFoundException(final UsernameNotFoundException ex) {
		UnauthorizedException uex = new UnauthorizedException("Unable to authenticate user");

		return new ResponseEntity<UnauthorizedException> (uex, uex.status);
	}

}




package com.aoede.modules.user.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.modules.user.config.UserConfiguration;
import com.aoede.modules.user.domain.User;
import com.aoede.modules.user.service.TokenService;

// TODO: should be added as part of the authentication filters
@Component
public class UserAuthenticationTokenFilter extends BaseComponent implements Filter {

	private TokenService tokenService;

	public UserAuthenticationTokenFilter (TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (
			req instanceof HttpServletRequest &&
			res instanceof HttpServletResponse &&
			(authentication == null || !authentication.isAuthenticated())
		) {
			authenticate ((HttpServletRequest)req, (HttpServletResponse)res);
		}

		chain.doFilter(req, res);

		SecurityContextHolder.clearContext();
	}

	private void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String token = request.getHeader(UserConfiguration.TOKEN_HEADER_NAME);

		if (token == null || token.isEmpty()) {
			return;
		}

		User user = tokenService.decodeUser(token);
		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken(user.getId(), null, user.getRoles()));

		if (request.getHeader(UserConfiguration.TOKEN_RENEW_FLAG) != null)
			attachToken (user, response);
	}

	private void attachToken(User user, HttpServletResponse response) throws IOException, ServletException {
		response.setHeader(UserConfiguration.TOKEN_HEADER_NAME,
			tokenService.encodeUser(user, UserConfiguration.TOKEN_TIME_TO_LIVE));

		response.addHeader(
			HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
			UserConfiguration.TOKEN_HEADER_NAME
		);
	}

}




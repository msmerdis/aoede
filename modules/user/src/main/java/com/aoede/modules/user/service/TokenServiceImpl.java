package com.aoede.modules.user.service;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.exceptions.GenericExceptionContainer;
import com.aoede.commons.base.exceptions.UnauthorizedException;
import com.aoede.modules.user.domain.User;
import com.aoede.modules.user.transfer.user.UserStatus;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenServiceImpl extends BaseComponent implements TokenService {

	private Key jwtKey;
	private JwtParser parser;

	public TokenServiceImpl (@Value("${secret:}") String secret) {
		// in case of an empty secret generate a random one
		if (secret.equals("")) {
			secret = new Random(System.currentTimeMillis())
				.ints(48, 123)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(64)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
		}

		jwtKey = Keys.hmacShaKeyFor(secret.getBytes());
		parser = Jwts.parserBuilder().setSigningKey(jwtKey).build();
	}

	@Override
	public String encode(Claims value) {
		return Jwts.builder().signWith(jwtKey, SignatureAlgorithm.HS256).setClaims(value).compact();
	}

	@Override
	public String encodeToken(String value) {
		Claims claims = Jwts.claims();

		claims.setSubject(value);

		return encode(claims);
	}

	@Override
	public String encodeToken(String value, int ttl) {
		Claims claims = Jwts.claims();

		claims.setSubject(value);
		claims.setExpiration(generateExpiration(ttl));

		return encode(claims);
	}

	@Override
	public String encodeToken(String value, String type) {
		Claims claims = Jwts.claims();

		claims.setSubject(value);
		claims.put("type", type);

		return encode(claims);
	}

	@Override
	public String encodeToken(String value, String type, int ttl) {
		Claims claims = Jwts.claims();

		claims.setSubject(value);
		claims.setExpiration(generateExpiration(ttl));
		claims.put("type", type);

		return encode(claims);
	}

	@Override
	public Claims decode(String token) {
		return parser.parseClaimsJws(token).getBody();
	}

	@Override
	public String decodeToken(String token) {
		return decode (token).getSubject();
	}

	@Override
	public String decodeToken(String token, String type) {
		Claims claims = decode (token);

		try {
			validateType (claims, type);
		} catch (GenericException e) {
			throw new GenericExceptionContainer(e);
		}

		return claims.getSubject();
	}

	public Date generateExpiration (int ttl) {
		Calendar date = Calendar.getInstance();

		date.add(Calendar.SECOND, ttl);

		return date.getTime();
	}

	public void validateType (Claims claims, String type) throws GenericException {
		if (!claims.containsKey("type")) {
			throw new UnauthorizedException("Token type not defined");
		}

		if (!claims.get("type").equals(type)) {
			throw new UnauthorizedException("Invalid token type");
		}
	}

	@Override
	public String encodeUser(User user, int ttl) {
		Claims claims = Jwts.claims ();

		claims.setExpiration(generateExpiration(ttl));
		claims.setSubject(user.getUsername());
		claims.put("userId", user.getId());
		claims.put("status", user.getStatus().toString());
		claims.put("roles",  encodeRoles(user.getRoles()));

		return encode(claims);
	}

	@Override
	public User decodeUser(String value) {
		User user = new User();
		Claims claims = decode(value);

		user.setUsername(claims.getSubject());
		user.setId(Long.parseLong(claims.get("userId").toString()));
		user.setStatus(UserStatus.valueOf((String)claims.get("status")));
		user.setRoles(decodeRoles(claims.get("roles")));

		return user;
	}

	public List<String> encodeRoles (Set<SimpleGrantedAuthority> roles) {
		return roles.stream().map(r -> r.getAuthority()).collect(Collectors.toList());
	}

	public Set<SimpleGrantedAuthority> decodeRoles (Object token) {
		if (token instanceof List<?>) {
			return ((List<?>) token).stream().map(a -> new SimpleGrantedAuthority(a.toString())).collect(Collectors.toSet());
		}

		return Set.of();
	}

}




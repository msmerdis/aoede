package com.aoede.modules.user.service;

import com.aoede.modules.user.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.jsonwebtoken.Claims;

public interface TokenService {
	public String encode (Claims value);
	public Claims decode (String token);

	public String encodeToken (String value);
	public String encodeToken (String value, int ttl);
	public String encodeToken (String value, String type);
	public String encodeToken (String value, String type, int ttl);
	public String decodeToken (String token);
	public String decodeToken (String token, String type);

	public <T> String encodeClass (T      value, Class<T> type) throws JsonProcessingException;
	public <T> T      decodeClass (String token, Class<T> type) throws JsonProcessingException, JsonMappingException;

	public String encodeUser (User   value, int ttl);
	public User   decodeUser (String value);
}




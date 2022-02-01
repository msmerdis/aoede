package com.aoede.modules.user.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Set;

import org.junit.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.aoede.TokenServiceImplTestCaseSetup;
import com.aoede.commons.base.exceptions.GenericExceptionContainer;
import com.aoede.modules.user.domain.User;
import com.aoede.modules.user.transfer.user.UserStatus;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

public class TestTokenServiceImpl extends TokenServiceImplTestCaseSetup {

	@Test
	public void decodeEncodedClaims () throws Exception {
		TokenServiceImpl uut = uut ();

		Claims claims = Jwts.claims();
		claims.setId("112");
		claims.setSubject("sub");

		String encoded = uut.encode(claims);
		Claims decoded = uut.decode(encoded);

		assertEquals("112", decoded.getId());
		assertEquals("sub", decoded.getSubject());
	}

	private void decodeEncodedToken (String value) throws Exception {
		TokenServiceImpl uut = uut ();

		String encoded = uut.encodeToken(value);
		String decoded = uut.decodeToken(encoded);

		assertEquals(value, decoded);
	}

	@Test
	public void decodeEncodedToken () throws Exception {
		decodeEncodedToken ("test");
	}

	@Test
	public void decodeEncodedTokenWithQuotes () throws Exception {
		decodeEncodedToken ("\"te\"st\"");
	}

	@Test
	public void decodeEncodedJson () throws Exception {
		decodeEncodedToken ("{\"key\":\"key\",\"value\":\"value\"}");
	}

	private void decodeEncodedTTLToken (String value, int ttl) throws Exception {
		TokenServiceImpl uut = uut ();

		String encoded = uut.encodeToken(value, ttl);
		String decoded = uut.decodeToken(encoded);

		assertEquals(value, decoded);
	}

	@Test
	public void decodeEncodedTTLToken () throws Exception {
		decodeEncodedTTLToken ("test", 100);
	}

	@Test
	public void decodeEncodedTTLTokenWithQuotes () throws Exception {
		decodeEncodedTTLToken ("\"te\"st\"", 100);
	}

	@Test
	public void decodeEncodedTTLJson () throws Exception {
		decodeEncodedTTLToken ("{\"key\":\"key\",\"value\":\"value\"}", 100);
	}

	@Test
	public void decodeEncodedTTLTokenExpired () throws Exception {
		assertThrows (ExpiredJwtException.class, () -> {
			decodeEncodedTTLToken ("123456", -1);
		});
	}

	private void decodeEncodedTypedToken (String value, String eType, String dType) throws Exception {
		TokenServiceImpl uut = uut ();

		String encoded = uut.encodeToken(value, eType);
		String decoded = uut.decodeToken(encoded, dType);

		assertEquals(value, decoded);
	}

	@Test
	public void decodeEncodedTypedToken () throws Exception {
		decodeEncodedTypedToken ("test", "user", "user");
	}

	@Test
	public void decodeEncodedTypedTokenWithQuotes () throws Exception {
		decodeEncodedTypedToken ("\"te\"st\"", "user", "user");
	}

	@Test
	public void decodeEncodedTypedJson () throws Exception {
		decodeEncodedTypedToken ("{\"key\":\"key\",\"value\":\"value\"}", "user", "user");
	}

	@Test
	public void decodeEncodedTypedTokenMismatch () throws Exception {
		assertThrows (GenericExceptionContainer.class, () -> {
			decodeEncodedTypedToken ("test", "user", "admin");
		});
	}

	private void decodeEncodedTypedTTLToken (String value, String eType, String dType, int ttl) throws Exception {
		TokenServiceImpl uut = uut ();

		String encoded = uut.encodeToken(value, eType, ttl);
		String decoded = uut.decodeToken(encoded, dType);

		assertEquals(value, decoded);
	}

	@Test
	public void decodeEncodedTypedTTLToken () throws Exception {
		decodeEncodedTypedTTLToken ("test", "user", "user", 100);
	}

	@Test
	public void decodeEncodedTypedTTLTokenWithQuotes () throws Exception {
		decodeEncodedTypedTTLToken ("\"te\"st\"", "user", "user", 100);
	}

	@Test
	public void decodeEncodedTypedTTLJson () throws Exception {
		decodeEncodedTypedTTLToken ("{\"key\":\"key\",\"value\":\"value\"}", "user", "user", 100);
	}

	@Test
	public void decodeEncodedTypedTTLTokenMismatch () throws Exception {
		assertThrows (GenericExceptionContainer.class, () -> {
			decodeEncodedTypedTTLToken ("test", "user", "admin", 100);
		});
	}

	@Test
	public void decodeEncodedTypedTTLTokenExpired () throws Exception {
		assertThrows (ExpiredJwtException.class, () -> {
			decodeEncodedTypedTTLToken ("test", "user", "user", -1);
		});
	}

	public static class Payload {
		public String key;
		public String value;

		public Payload() {
		}
	}

	@Test
	public void decodeEncodedClass () throws Exception {
		TokenServiceImpl uut = uut ();

		Payload payload = new Payload();
		payload.key = "key";
		payload.value = "value";

		String  encoded = uut.encodeClass(payload, Payload.class);
		Payload decoded = uut.decodeClass(encoded, Payload.class);

		assertEquals("key",   decoded.key);
		assertEquals("value", decoded.value);
	}

	@Test
	public void decodeEncodedUser () throws Exception {
		TokenServiceImpl uut = uut ();

		User user = new User();

		user.setId(1234L);
		user.setUsername("the user name");
		user.setStatus(UserStatus.ACTIVE);

		String encoded = uut.encodeUser(user, 100);
		User   decoded = uut.decodeUser(encoded);

		assertEquals(user.getId(),       decoded.getId());
		assertEquals(user.getUsername(), decoded.getUsername());
		assertEquals(user.getStatus(),   decoded.getStatus());
		assertEquals(0, decoded.getAuthorities().size());
	}

	@Test
	public void decodeEncodedUserWithRoles () throws Exception {
		TokenServiceImpl uut = uut ();

		User user = new User();

		user.setId(1234567890L);
		user.setUsername("the user's username value");
		user.setStatus(UserStatus.ACTIVE);
		user.setRoles(Set.of(
			new SimpleGrantedAuthority("VIEW"),
			new SimpleGrantedAuthority("PACK"),
			new SimpleGrantedAuthority("SORT")
		));

		String encoded = uut.encodeUser(user, 100);
		User   decoded = uut.decodeUser(encoded);

		assertEquals(user.getId(),       decoded.getId());
		assertEquals(user.getUsername(), decoded.getUsername());
		assertEquals(user.getStatus(),   decoded.getStatus());
		assertEquals(3, decoded.getAuthorities().size());

		assertEquals(user.getAuthorities(), decoded.getAuthorities());
	}

}




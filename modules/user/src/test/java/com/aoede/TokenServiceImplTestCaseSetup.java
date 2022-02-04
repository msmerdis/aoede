package com.aoede;

import java.lang.reflect.Field;
import java.util.Random;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.aoede.commons.cucumber.BaseTestComponent;
import com.aoede.modules.user.service.TokenServiceImpl;

@RunWith(SpringRunner.class)
public class TokenServiceImplTestCaseSetup extends BaseTestComponent {

	// unit under test
	protected TokenServiceImpl uut () throws Exception {
		String secret = new Random(0).ints(48, 123)
			.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
			.limit(86)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();

		return new TokenServiceImpl(secret);
	}

	protected void setField (TokenServiceImpl uut, String fieldName, Object value) throws Exception {
		Field field = TokenServiceImpl.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(uut, value);
	}

	protected Object getField (TokenServiceImpl uut, String fieldName) throws Exception {
		Field field = TokenServiceImpl.class.getDeclaredField(fieldName);

		field.setAccessible(true);

		return field.get(uut);
	}

}




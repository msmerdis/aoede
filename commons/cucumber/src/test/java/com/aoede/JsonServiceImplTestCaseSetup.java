package com.aoede;

import java.lang.reflect.Field;
import java.util.Random;

import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.aoede.commons.cucumber.BaseTestComponent;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.JsonServiceImpl;

@RunWith(SpringRunner.class)
public class JsonServiceImplTestCaseSetup extends BaseTestComponent {

	protected Random random = new Random(0);

	@MockBean protected AbstractTestServiceDiscoveryService abstractTestServiceDiscoveryService;

	// unit under test
	protected JsonServiceImpl uut () throws Exception {
		JsonServiceImpl uut = new JsonServiceImpl (
			this.abstractTestServiceDiscoveryService
		);

		setField (uut, "random", this.random);

		return uut;
	}

	protected void setField (JsonServiceImpl uut, String fieldName, Object value) throws Exception {
		Field field = JsonServiceImpl.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(uut, value);
	}

	protected Object getField (JsonServiceImpl uut, String fieldName) throws Exception {
		Field field = JsonServiceImpl.class.getDeclaredField(fieldName);

		field.setAccessible(true);

		return field.get(uut);
	}

}




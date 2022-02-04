package com.aoede;

import java.lang.reflect.Field;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.aoede.commons.cucumber.BaseTestComponent;
import com.aoede.commons.cucumber.service.AbstractTestServiceImpl;

@RunWith(SpringRunner.class)
public class AbstractTestServiceImplTestCaseSetup extends BaseTestComponent {

	// unit under test
	protected AbstractTestServiceImpl uut () throws Exception {
		AbstractTestServiceImpl uut = new AbstractTestServiceImpl () {

			@Override
			public String getName() {
				return "the name";
			}

			@Override
			public String getPath() {
				return "the path";
			}

			@Override
			public String getKeyName() {
				return "the key";
			}

		};

		return uut;
	}

	protected void setField (AbstractTestServiceImpl uut, String fieldName, Object value) throws Exception {
		Field field = AbstractTestServiceImpl.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(uut, value);
	}

	protected Object getField (AbstractTestServiceImpl uut, String fieldName) throws Exception {
		Field field = AbstractTestServiceImpl.class.getDeclaredField(fieldName);

		field.setAccessible(true);

		return field.get(uut);
	}

}




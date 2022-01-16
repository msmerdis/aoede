package com.aoede;

import java.lang.reflect.Field;

import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.aoede.commons.base.BaseTestComponent;
import com.aoede.commons.service.CompositeIdServiceImpl;
import com.aoede.commons.service.JsonObjectService;

@RunWith(SpringRunner.class)
public class CompositeIdServiceImplTestCaseSetup extends BaseTestComponent {

	@MockBean
	protected JsonObjectService jsonObjectService;

	// unit under test
	protected CompositeIdServiceImpl uut () throws Exception {
		CompositeIdServiceImpl uut = new CompositeIdServiceImpl ();

		setField (uut, "jsonObjectService", this.jsonObjectService);

		return uut;
	}

	protected void setField (CompositeIdServiceImpl uut, String fieldName, Object value) throws Exception {
		Field field = CompositeIdServiceImpl.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(uut, value);
	}

	protected Object getField (CompositeIdServiceImpl uut, String fieldName) throws Exception {
		Field field = CompositeIdServiceImpl.class.getDeclaredField(fieldName);

		field.setAccessible(true);

		return field.get(uut);
	}

}




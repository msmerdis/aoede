package com.aoede.commons.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Base64;

import org.junit.Test;

import com.aoede.CompositeIdServiceImplTestCaseSetup;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;

public class TestCompositeIdServiceImpl extends CompositeIdServiceImplTestCaseSetup {

	@Test
	public void verifyGenerateBase64 () throws Exception {
		String data = "some random data";
		String pack = uut().generateBase64(data);

		assertEquals ("data where not generated correctly", "c29tZSByYW5kb20gZGF0YQ==", pack);
		assertEquals ("data where not generated correctly", data, new String(Base64.getDecoder().decode(pack)));
	}

	@Test
	public void verifyGeneratePaddedBase64 () throws Exception {
		String data = "some random data";
		String pack = uut().generatePaddedBase64(data);

		assertEquals ("data where not generated correctly", "c29tZSByYW5kb20gZGF0YSAg", pack);
		assertEquals ("data where not generated correctly", data + "  ", new String(Base64.getDecoder().decode(pack)));
	}

	@Test
	public void verifyGeneratePaddedBase64NoPadding () throws Exception {
		String data = "some randomer data";
		String pack = uut().generatePaddedBase64(data);

		assertEquals ("data where not generated correctly", "c29tZSByYW5kb21lciBkYXRh", pack);
		assertEquals ("data where not generated correctly", data, new String(Base64.getDecoder().decode(pack)));
	}

	@Test
	public void verifyGenerateCompositeKey () throws Exception {
		JsonObject object = new JsonObject ();

		object.add("parentId", new JsonPrimitive(1));
		object.add( "childId", new JsonPrimitive(1));

		when(jsonObjectService.generateJson(any())).thenReturn(object);

		String pack = uut().generateCompositeKey(DataTable.emptyDataTable());

		assertEquals ("data where not generated correctly", "eyJwYXJlbnRJZCI6MSwiY2hpbGRJZCI6MX0g", pack);
		assertEquals ("data where not generated correctly", "{\"parentId\":1,\"childId\":1} ", new String(Base64.getDecoder().decode(pack)));
	}

}




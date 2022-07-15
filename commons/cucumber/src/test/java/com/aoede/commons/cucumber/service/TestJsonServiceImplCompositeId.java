package com.aoede.commons.cucumber.service;

import static org.junit.Assert.assertEquals;

import java.util.Base64;
import java.util.List;

import org.junit.Test;

import com.aoede.JsonServiceImplTestCaseSetup;

import io.cucumber.datatable.DataTable;

public class TestJsonServiceImplCompositeId extends JsonServiceImplTestCaseSetup {

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
		DataTable table = buildDataTable (List.of(
			List.of("parentId", "integer", "1"),
			List.of( "childId", "integer", "1")
		));

		String pack = uut().generateCompositeKey(table);

		assertEquals ("data where not generated correctly", "eyJwYXJlbnRJZCI6MSwiY2hpbGRJZCI6MX0g", pack);
		assertEquals ("data where not generated correctly", "{\"parentId\":1,\"childId\":1} ", new String(Base64.getDecoder().decode(pack)));
	}

}




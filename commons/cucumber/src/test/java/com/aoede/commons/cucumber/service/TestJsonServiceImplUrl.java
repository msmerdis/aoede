package com.aoede.commons.cucumber.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;

import org.junit.Test;

import com.aoede.JsonServiceImplTestCaseSetup;

import io.cucumber.datatable.DataTable;

public class TestJsonServiceImplUrl extends JsonServiceImplTestCaseSetup {

	@Test
	public void verifyGenerateUrlSuccess () throws Exception {
		var uut = uut();

		DataTable table = buildDataTable (List.of(
			List.of("string", "hello"),
			List.of("int", "1"),
			List.of("bool", "true"),
			List.of("compositeId", "cid")
		));

		uut.putCompositeKey("cid", buildDataTable(List.of(
			List.of("cid", "string", "test")
		)));

		String url = uut.generateUrl(table);

		assertEquals ("data where not generated correctly", "/hello/1/true/eyJjaWQiOiJ0ZXN0In0g", url);
	}

	@Test
	public void verifyGenerateUrlJsonFailure () throws Exception {
		var uut = uut();

		DataTable table = buildDataTable (List.of(
			List.of("json", "test")
		));

		assertThrows ("json should not be a valid option for urls", AssertionError.class, () -> {
			uut.generateUrl(table);
		});
	}

	@Test
	public void verifyGenerateUrlFractionFailure () throws Exception {
		var uut = uut();

		DataTable table = buildDataTable (List.of(
			List.of("fraction", "1/2")
		));

		assertThrows ("fraction should not be a valid option for urls", AssertionError.class, () -> {
			uut.generateUrl(table);
		});
	}

}




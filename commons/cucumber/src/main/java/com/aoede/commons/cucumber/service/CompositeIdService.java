package com.aoede.commons.cucumber.service;

import io.cucumber.datatable.DataTable;

public interface CompositeIdService extends TestStorageService<String> {

	String generateBase64(String data);
	String generatePaddedBase64(String data);
	String generateCompositeKey(DataTable data);

}




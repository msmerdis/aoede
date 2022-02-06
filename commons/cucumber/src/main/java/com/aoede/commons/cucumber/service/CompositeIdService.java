package com.aoede.commons.cucumber.service;

import io.cucumber.datatable.DataTable;

public interface CompositeIdService extends TestStorageService<String> {

	void updateJsonObjectService(JsonObjectService jsonObjectService);

	String generateBase64(String data);
	String generatePaddedBase64(String data);
	String generateCompositeKey(DataTable data);

}




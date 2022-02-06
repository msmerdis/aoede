package com.aoede.commons.cucumber.service;

import static org.junit.Assert.assertNotNull;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import io.cucumber.datatable.DataTable;

@Service
public class CompositeIdServiceImpl extends TestStorageServiceImpl<String> implements CompositeIdService {

	private JsonObjectService jsonObjectService;

	public CompositeIdServiceImpl () {
	}

	@Override
	public void updateJsonObjectService(JsonObjectService jsonObjectService) {
		this.jsonObjectService = jsonObjectService;
	}

	@Override
	public String generateBase64 (String data) {
		return Base64.encodeBase64String(data.getBytes());
	}

	@Override
	public String generatePaddedBase64 (String data) {
		switch (data.length() % 3) {
			case 1: data = data + "  "; break;
			case 2: data = data + " ";  break;
			default:
				// no padding is needed
		}
		return generateBase64 (data);
	}

	@Override
	public String generateCompositeKey (DataTable data) {
		assertNotNull("json object service is null", jsonObjectService);
		return generatePaddedBase64(
			jsonObjectService.generateJson(data).toString()
		);
	}

	@Override
	public String put(String name, DataTable data) {
		return put(name, generateCompositeKey(data));
	}

}




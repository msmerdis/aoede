package com.aoede.commons.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import io.cucumber.datatable.DataTable;

@Service
public class CompositeIdServiceImpl extends TestStorageServiceImpl<String> implements CompositeIdService {

	@Lazy
	@Autowired
	private JsonObjectService jsonObjectService;

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
		return generatePaddedBase64(
			jsonObjectService.generateJson(data).toString()
		);
	}

	@Override
	public void put(String name, DataTable data) {
		put(name, generateCompositeKey(data));
	}

}




package com.aoede.commons.service;

import java.util.HashMap;

import org.apache.tomcat.util.codec.binary.Base64;

import io.cucumber.datatable.DataTable;
import lombok.Setter;

@Setter
public class CompositeIdServiceImpl extends HashMap<String, String> implements CompositeIdService {
	private static final long serialVersionUID = 1L;

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




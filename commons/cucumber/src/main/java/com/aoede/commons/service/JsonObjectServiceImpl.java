package com.aoede.commons.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.datatable.DataTable;
import lombok.Setter;

@Setter
public class JsonObjectServiceImpl extends HashMap<String, JsonObject> implements JsonObjectService {
	private static final long serialVersionUID = 1L;

	private CompositeIdService compositeIdService;

	// generate a json object base on data table
	@Override
	public JsonObject generateJson (DataTable data) {
		JsonObject obj = new JsonObject ();

		for (var row : data.asLists()) {
			assertEquals("Json row must have exactly three elements", 3, row.size());
			switch (row.get(1)) {
			case "int":
			case "integer":
				obj.add(row.get(0), new JsonPrimitive(Integer.parseInt(row.get(2))));
			case "long":
				obj.add(row.get(0), new JsonPrimitive(Long.parseLong(row.get(2))));
				break;
			case "number":
				obj.add(row.get(0), new JsonPrimitive(new BigInteger(row.get(2))));
				break;
			case "string":
				obj.add(row.get(0), new JsonPrimitive(row.get(2)));
				break;
			case "bool":
			case "boolean":
				obj.add(row.get(0), new JsonPrimitive(Boolean.parseBoolean(row.get(2))));
				break;
			case "compositeId":
				assertTrue (
					"composite id " + row.get(1) + " was not found",
					compositeIdService.containsKey(row.get(1))
				);
				obj.add(row.get(0), new JsonPrimitive(compositeIdService.get(row.get(1))));
				break;
			case "json":
				assertTrue (
					"json " + row.get(1) + " was not found",
					containsKey(row.get(1))
				);
				obj.add(row.get(0), get(row.get(1)));
				break;
			default:
				assertFalse ("Type " + row.get(1) + " could not be converted to json value", true);
			}
		}

		return obj;
	}

	@Override
	public void put(String name, DataTable data) {
		put(name, generateJson(data));
	}
}




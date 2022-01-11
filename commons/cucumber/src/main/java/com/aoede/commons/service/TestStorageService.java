package com.aoede.commons.service;

import java.util.Map;

import io.cucumber.datatable.DataTable;

public interface TestStorageService <Data> extends Map<String, Data> {
	void put (String name, DataTable data);
}




package com.aoede.commons.cucumber;

import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aoede.commons.cucumber.service.AbstractTestService;

import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableTypeRegistry;
import io.cucumber.datatable.DataTableTypeRegistryTableConverter;

public class BaseTestComponent {
	// Define a logger for all test components
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@PostConstruct
	private void init() {
		logger.debug("Loaded {}.", getClass().getName());
	}

	@PreDestroy
	private void destroy() {
		logger.debug("Ready to destroy {}.", getClass().getName());
	}

	protected DataTable buildDataTable (List<List<String>> data) {
		return DataTable.create(data,
			new DataTableTypeRegistryTableConverter (
				new DataTableTypeRegistry (Locale.ENGLISH)
			)
		);
	}

	protected AbstractTestService createLatestServiceMock() {
		return Mockito.mock(AbstractTestService.class);
	}

}




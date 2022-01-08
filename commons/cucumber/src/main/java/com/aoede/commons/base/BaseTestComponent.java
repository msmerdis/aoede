package com.aoede.commons.base;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTestComponent extends HashMap<String, String> {
	private static final long serialVersionUID = 1L;

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
}




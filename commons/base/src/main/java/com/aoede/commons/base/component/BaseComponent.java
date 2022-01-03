package com.aoede.commons.base.component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class used to provide logging functionality to all components.
 */
public abstract class BaseComponent {
	// Define a logger for all components
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




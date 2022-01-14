package com.aoede;

import org.springframework.boot.test.autoconfigure.actuate.metrics.AutoConfigureMetrics;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import com.aoede.commons.base.BaseTestComponent;

import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;

@AutoConfigureMetrics
@CucumberContextConfiguration
@SpringBootTest(classes = {
	AoedeApplication.class, CucumberTest.class},
	webEnvironment = WebEnvironment.DEFINED_PORT
)
@ActiveProfiles("test")
public class CucumberSpringContextConfiguration extends BaseTestComponent {
	/**
	 * Need this method so the cucumber will recognise this class as glue and load spring context configuration
	 */
	@Before
	public void setUp() {
		logger.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
	}
}




package com.aoede;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.aoede.commons.base.BaseTestComponent;

import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = {
	TestApplication.class, CucumberTest.class},
	webEnvironment = WebEnvironment.DEFINED_PORT
)
public class CucumberSpringContextConfiguration extends BaseTestComponent {
	/**
	 * Need this method so the cucumber will recognise this class as glue and load spring context configuration
	 */
	@Before
	public void setUp() {
		logger.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
	}
}




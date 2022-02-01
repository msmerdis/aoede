package com.aoede;

import org.junit.runner.RunWith;

import com.aoede.commons.base.BaseTestComponent;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
	plugin = {"pretty", "com.aoede.commons.base.BaseStepDefinition"},
	tags = "@Regression and not @Skip",
	features = {"src/test/resources/features"}
)
public class CucumberTest extends BaseTestComponent {
}




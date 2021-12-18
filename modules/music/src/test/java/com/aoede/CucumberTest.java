package com.aoede;

import org.junit.runner.RunWith;

import com.aoede.commons.base.component.BaseComponent;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
	plugin = {"pretty", "com.aoede.commons.base.BaseStepDefinition"},
	tags = "",
	features = {"src/test/resources/features"}
)
public class CucumberTest extends BaseComponent {
}




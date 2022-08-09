package com.aoede;

import java.util.Random;

import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.aoede.commons.cucumber.BaseTestComponent;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.JsonServiceImpl;
import com.aoede.commons.cucumber.service.generators.RandomStringGenerator;

@RunWith(SpringRunner.class)
public class JsonServiceImplTestCaseSetup extends BaseTestComponent {

	@MockBean protected AbstractTestServiceDiscoveryService abstractTestServiceDiscoveryService;

	// unit under test
	protected JsonServiceImpl uut () throws Exception {
		JsonServiceImpl uut = new JsonServiceImpl (
			this.abstractTestServiceDiscoveryService
		);

		uut.putGenerator("random string", new RandomStringGenerator(new Random(0)));

		return uut;
	}

}




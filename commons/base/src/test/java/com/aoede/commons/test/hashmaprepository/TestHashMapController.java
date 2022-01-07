package com.aoede.commons.test.hashmaprepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractDomainController;

@RestController
@RequestMapping ("/api/test/hashmaprepository")
public class TestHashMapController extends AbstractDomainController<Integer, TestHashMapDomain, TestHashMapService> {

	public TestHashMapController(TestHashMapService service) {
		super(service);
	}

}




package com.aoede.commons.base.maprepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractDomainController;

@RestController
@RequestMapping ("/api/test/maprepository")
public class TestMapController extends AbstractDomainController<Integer, TestMapDomain, TestMapService> {

	public TestMapController(TestMapService service) {
		super(service);
	}

}




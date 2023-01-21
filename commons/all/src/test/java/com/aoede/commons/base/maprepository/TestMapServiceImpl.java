package com.aoede.commons.base.maprepository;

import org.springframework.stereotype.Service;

import com.aoede.commons.base.service.AbstractServiceEntityImpl;

@Service
public class TestMapServiceImpl extends AbstractServiceEntityImpl <Integer, TestMapDomain, TestMapRepository> implements TestMapService {

	public TestMapServiceImpl(TestMapRepository repository) {
		super(repository);
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return "TestMapDomain";
	}

}




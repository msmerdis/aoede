package com.aoede.commons.base.hashmaprepository;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Service;

import com.aoede.commons.base.service.AbstractServiceEntityImpl;

@Service
public class TestHashMapServiceImpl extends AbstractServiceEntityImpl <Integer, TestHashMapDomain, TestHashMapRepository> implements TestHashMapService {

	public TestHashMapServiceImpl(TestHashMapRepository repository, EntityManagerFactory entityManagerFactory) {
		super(repository, entityManagerFactory);
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return "TestHashMapDomain";
	}

}




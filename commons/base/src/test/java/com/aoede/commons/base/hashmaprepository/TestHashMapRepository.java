package com.aoede.commons.base.hashmaprepository;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.HashMapRepository;

@Repository
public class TestHashMapRepository extends HashMapRepository <Integer, TestHashMapDomain> {

	@PostConstruct
	public void init () {
		save(new TestHashMapDomain (1, "one"));
		save(new TestHashMapDomain (2, "two"));
		save(new TestHashMapDomain (3, "three"));
	}

}




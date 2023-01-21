package com.aoede.commons.base.maprepository;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.MapRepository;

@Repository
public class TestMapRepository extends MapRepository <Integer, TestMapDomain> {

	@PostConstruct
	public void init () {
		save(new TestMapDomain (1, "one"));
		save(new TestMapDomain (2, "two"));
		save(new TestMapDomain (3, "three"));
	}

}




package com.aoede.modules.music.service;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Service;

import com.aoede.commons.base.service.AbstractServiceEntityImpl;
import com.aoede.modules.music.domain.Clef;
import com.aoede.modules.music.repository.ClefRepository;

@Service
public class ClefService extends AbstractServiceEntityImpl <String, Clef, ClefRepository> {

	public ClefService(ClefRepository repository, EntityManagerFactory entityManagerFactory) {
		super(repository, entityManagerFactory);
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return "Clef";
	}

}




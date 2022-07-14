package com.aoede.modules.music.service;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Service;

import com.aoede.commons.base.service.AbstractServiceEntityImpl;
import com.aoede.modules.music.domain.Octave;
import com.aoede.modules.music.repository.OctaveRepository;

@Service
public class OctaveServiceImpl extends AbstractServiceEntityImpl <Integer, Octave, OctaveRepository> implements OctaveService {

	public OctaveServiceImpl(OctaveRepository repository, EntityManagerFactory entityManagerFactory) {
		super(repository, entityManagerFactory);
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return "Octave";
	}

}




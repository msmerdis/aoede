package com.aoede.modules.music.service;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Service;

import com.aoede.commons.base.service.AbstractServiceEntityImpl;
import com.aoede.modules.music.domain.Tempo;
import com.aoede.modules.music.repository.TempoRepository;

@Service
public class TempoServiceImpl extends AbstractServiceEntityImpl <String, Tempo, TempoRepository> implements TempoService {

	public TempoServiceImpl(TempoRepository repository, EntityManagerFactory entityManagerFactory) {
		super(repository, entityManagerFactory);
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return "TimeSignature";
	}

}




package com.aoede.modules.music.service;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Service;

import com.aoede.commons.base.service.AbstractServiceEntityImpl;
import com.aoede.modules.music.domain.Fraction;
import com.aoede.modules.music.domain.TimeSignature;
import com.aoede.modules.music.repository.TimeSignatureRepository;

@Service
public class TimeSignatureServiceImpl extends AbstractServiceEntityImpl <Fraction, TimeSignature, TimeSignatureRepository> implements TimeSignatureService {

	public TimeSignatureServiceImpl(TimeSignatureRepository repository, EntityManagerFactory entityManagerFactory) {
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




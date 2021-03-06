package com.aoede.modules.music.service;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Service;

import com.aoede.commons.base.service.AbstractServiceEntityImpl;
import com.aoede.modules.music.domain.KeySignature;
import com.aoede.modules.music.repository.KeySignatureRepository;

@Service
public class KeySignatureServiceImpl extends AbstractServiceEntityImpl <Short, KeySignature, KeySignatureRepository> implements KeySignatureService {

	public KeySignatureServiceImpl(KeySignatureRepository repository, EntityManagerFactory entityManagerFactory) {
		super(repository, entityManagerFactory);
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return "KeySignature";
	}

	@Override
	public KeySignature findByMajor (String major) {
		return repository.findAll()
			.stream()
			.filter(key -> key.getMajor().equals(major))
			.findFirst()
			.orElseThrow();
	}

	@Override
	public KeySignature findByMinor (String minor) {
		return repository.findAll()
			.stream()
			.filter(key -> key.getMinor().equals(minor))
			.findFirst()
			.orElseThrow();
	}
}




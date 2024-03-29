package com.aoede.modules.music.service;

import org.springframework.stereotype.Service;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.exceptions.NotFoundException;
import com.aoede.commons.base.service.AbstractServiceEntityImpl;
import com.aoede.modules.music.domain.KeySignature;
import com.aoede.modules.music.repository.KeySignatureRepository;

@Service
public class KeySignatureServiceImpl extends AbstractServiceEntityImpl <Short, KeySignature, KeySignatureRepository> implements KeySignatureService {

	public KeySignatureServiceImpl(KeySignatureRepository repository) {
		super(repository);
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
	public KeySignature findByMajor (String major) throws GenericException {
		return repository.findAll()
			.stream()
			.filter(key -> key.getMajor().equals(major))
			.findFirst()
			.orElseThrow(() -> new NotFoundException("Key signature not found"));
	}

	@Override
	public KeySignature findByMinor (String minor) throws GenericException {
		return repository.findAll()
			.stream()
			.filter(key -> key.getMinor().equals(minor))
			.findFirst()
			.orElseThrow(() -> new NotFoundException("Key signature not found"));
	}
}




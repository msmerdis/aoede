package com.aoede.modules.music.repository;

import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.MapRepository;
import com.aoede.modules.music.domain.KeySignature;

@Repository
public class KeySignatureRepository extends MapRepository <Short, KeySignature> {

	public KeySignatureRepository () {
		super (new TreeMap <Short, KeySignature>());
	}

	@PostConstruct
	public void init () {
		save(new KeySignature ((short) -7, "C-", "a-"));
		save(new KeySignature ((short) -6, "G-", "e-"));
		save(new KeySignature ((short) -5, "D-", "b-"));
		save(new KeySignature ((short) -4, "A-", "f"));
		save(new KeySignature ((short) -3, "E-", "c"));
		save(new KeySignature ((short) -2, "B-", "g"));
		save(new KeySignature ((short) -1, "F" , "d"));
		save(new KeySignature ((short)  0, "C" , "a"));
		save(new KeySignature ((short)  1, "G" , "e"));
		save(new KeySignature ((short)  2, "D" , "b"));
		save(new KeySignature ((short)  3, "A" , "f+"));
		save(new KeySignature ((short)  4, "E" , "c+"));
		save(new KeySignature ((short)  5, "B" , "g+"));
		save(new KeySignature ((short)  6, "F+", "d+"));
		save(new KeySignature ((short)  7, "C+", "a+"));
	}

}




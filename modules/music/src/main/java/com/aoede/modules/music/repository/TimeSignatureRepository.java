package com.aoede.modules.music.repository;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.MapRepository;
import com.aoede.modules.music.domain.Fraction;
import com.aoede.modules.music.domain.TimeSignature;

@Repository
public class TimeSignatureRepository extends MapRepository <Fraction, TimeSignature> {

	public TimeSignatureRepository () {
		super (new LinkedHashMap <Fraction, TimeSignature>());
	}

	@PostConstruct
	public void init () {
		save(new TimeSignature (2, 4, List.of(0, 1)));
		save(new TimeSignature (2, 2, List.of(0, 1)));

		save(new TimeSignature (3, 8, List.of(0, 1, 2)));
		save(new TimeSignature (3, 4, List.of(0, 1, 2)));
		save(new TimeSignature (3, 2, List.of(0, 1, 2)));

		save(new TimeSignature (4, 8, List.of(0, 1, 2, 3)));
		save(new TimeSignature (4, 4, List.of(0, 1, 2, 3)));
		save(new TimeSignature (4, 2, List.of(0, 1, 2, 3)));

		save(new TimeSignature (6, 8, List.of(0, 3)));
		save(new TimeSignature (6, 4, List.of(0, 3)));

		save(new TimeSignature (9, 8, List.of(0, 3, 6)));
		save(new TimeSignature (9, 4, List.of(0, 3, 6)));

		save(new TimeSignature (12, 8, List.of(0, 3, 6, 9)));
		save(new TimeSignature (12, 4, List.of(0, 3, 6, 9)));
	}

}




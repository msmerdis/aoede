package com.aoede.modules.music.repository;

import java.util.LinkedHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.MapRepository;
import com.aoede.modules.music.domain.Clef;

@Repository
public class ClefRepository extends MapRepository <String, Clef> {

	public ClefRepository () {
		super (new LinkedHashMap <String, Clef>());
	}

	@PostConstruct
	public void init () {
		save(new Clef ("French Violin", 'G', 64, -4));
		save(new Clef ("Treble",        'G', 64, -2));
		save(new Clef ("Soprano",       'C', 60, -4));
		save(new Clef ("Mezzo-soprano", 'C', 60, -2));
		save(new Clef ("Alto",          'C', 60,  0));
		save(new Clef ("Tenor",         'C', 60,  2));
		save(new Clef ("Baritone",      'F', 53,  0));
		save(new Clef ("Bass",          'F', 53,  2));
		save(new Clef ("Subbass",       'F', 53,  4));
	}

}




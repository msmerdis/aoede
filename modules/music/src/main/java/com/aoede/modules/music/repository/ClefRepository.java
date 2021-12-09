package com.aoede.modules.music.repository;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.HashMapRepository;
import com.aoede.modules.music.domain.Clef;

@Repository
public class ClefRepository extends HashMapRepository <String, Clef> {

	@PostConstruct
	public void init () {
		save(buildClef ("French Violin", 'G', 64, -4));
		save(buildClef ("Treble",        'G', 64, -2));
		save(buildClef ("Soprano",       'C', 60, -4));
		save(buildClef ("Mezzo-soprano", 'C', 60, -2));
		save(buildClef ("Alto",          'C', 60,  0));
		save(buildClef ("Tenor",         'C', 60,  2));
		save(buildClef ("Baritone",      'F', 53,  0));
		save(buildClef ("Bass",          'F', 53,  2));
		save(buildClef ("Subbass",       'F', 53,  4));
	}

	private Clef buildClef (String name, char type, int note, int spos) {
		Clef clef = new Clef (type, note, spos);

		clef.setId(name);

		return clef;
	}
}




package com.aoede.modules.music.repository;

import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.MapRepository;
import com.aoede.modules.music.domain.Octave;

@Repository
public class OctaveRepository extends MapRepository <Integer, Octave> {

	public OctaveRepository () {
		super (new TreeMap <Integer, Octave>());
	}

	@PostConstruct
	public void init () {
		save(new Octave (-1, "subsubcontra",   0));
		save(new Octave ( 0,   "sub-contra",  12));
		save(new Octave ( 1,       "contra",  24));
		save(new Octave ( 2,        "great",  36));
		save(new Octave ( 3,        "small",  48));
		save(new Octave ( 4,    "one-lined",  60));
		save(new Octave ( 5,    "two-lined",  72));
		save(new Octave ( 6,  "three-lined",  84));
		save(new Octave ( 7,   "four-lined",  96));
		save(new Octave ( 8,   "five-lined", 108));
		save(new Octave ( 9,    "six-lined", 120));
	}

}




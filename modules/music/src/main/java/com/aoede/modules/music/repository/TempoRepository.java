package com.aoede.modules.music.repository;

import java.util.LinkedHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.MapRepository;
import com.aoede.modules.music.domain.Clef;
import com.aoede.modules.music.domain.Tempo;

@Repository
public class TempoRepository extends MapRepository <String, Tempo> {

	public TempoRepository () {
		super (new LinkedHashMap <String, Tempo>());
	}

	@PostConstruct
	public void init () {
		save(new Tempo ("Larghissimo",      "very, very slow",                      (short)   0, (short) 100, (short)  24,  true));
		save(new Tempo ("Adagissimo",       "very slow",                            (short)  24, (short) 100, (short)  40, false));
		save(new Tempo ("Grave",            "very slow",                            (short)  25, (short) 100, (short)  45,  true));
		save(new Tempo ("Largo",            "slow and broad",                       (short)  40, (short) 100, (short)  60, false));
		save(new Tempo ("Lento",            "slow",                                 (short)  45, (short) 100, (short)  60,  true));
		save(new Tempo ("Larghetto",        "rather slow and broad",                (short)  60, (short) 100, (short)  66,  true));
		save(new Tempo ("Adagio",           "slow with great expression",           (short)  66, (short) 100, (short)  76,  true));
		save(new Tempo ("Adagietto",        "slower than andante",                  (short)  72, (short) 100, (short)  76, false));
		save(new Tempo ("Andante",          "at a walking pace",                    (short)  76, (short) 100, (short) 108,  true));
		save(new Tempo ("Andantino",        "slightly faster than andante",         (short)  80, (short) 100, (short) 108, false));
		save(new Tempo ("Marcia moderato",  "moderately, in the manner of a march", (short)  83, (short) 100, (short)  85, false));
		save(new Tempo ("Andante moderato", "between andante and moderato",         (short)  92, (short) 100, (short) 112, false));
		save(new Tempo ("Moderato",         "at a moderate speed",                  (short) 108, (short) 100, (short) 120,  true));
		save(new Tempo ("Allegretto",       "moderately fast",                      (short) 112, (short) 100, (short) 120, false));
		save(new Tempo ("Allegro moderato", "close to, but not quite allegro",      (short) 116, (short) 100, (short) 120, false));
		save(new Tempo ("Allegro",          "fast, quick, and bright",              (short) 120, (short) 100, (short) 156,  true));
		save(new Tempo ("Molto Allegro",    "very fast",                            (short) 124, (short) 100, (short) 156, false));
		save(new Tempo ("Vivace",           "lively and fast",                      (short) 156, (short) 100, (short) 176,  true));
		save(new Tempo ("Vivacissimo",      "very fast and lively",                 (short) 172, (short) 100, (short) 176, false));
		save(new Tempo ("Allegrissimo",     "very fast",                            (short) 172, (short) 100, (short) 176, false));
		save(new Tempo ("Presto",           "very, very fast",                      (short) 168, (short) 100, (short) 200,  true));
		save(new Tempo ("Prestissimo",      "even faster than presto",              (short) 200, (short) 100, (short) 512,  true));
	}

}




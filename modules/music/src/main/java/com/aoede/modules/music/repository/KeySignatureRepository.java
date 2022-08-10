package com.aoede.modules.music.repository;

import java.util.List;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.MapRepository;
import com.aoede.modules.music.domain.KeySignature;
import com.aoede.modules.music.domain.NoteOffset;

@Repository
public class KeySignatureRepository extends MapRepository <Short, KeySignature> {

	public KeySignatureRepository () {
		super (new TreeMap <Short, KeySignature>());
	}

	@PostConstruct
	public void init () {
		save(new KeySignature ((short) -7, "C-", "a-", build((short) -7)));
		save(new KeySignature ((short) -6, "G-", "e-", build((short) -6)));
		save(new KeySignature ((short) -5, "D-", "b-", build((short) -5)));
		save(new KeySignature ((short) -4, "A-", "f",  build((short) -4)));
		save(new KeySignature ((short) -3, "E-", "c",  build((short) -3)));
		save(new KeySignature ((short) -2, "B-", "g",  build((short) -2)));
		save(new KeySignature ((short) -1, "F" , "d",  build((short) -1)));
		save(new KeySignature ((short)  0, "C" , "a",  build((short)  0)));
		save(new KeySignature ((short)  1, "G" , "e",  build((short)  1)));
		save(new KeySignature ((short)  2, "D" , "b",  build((short)  2)));
		save(new KeySignature ((short)  3, "A" , "f+", build((short)  3)));
		save(new KeySignature ((short)  4, "E" , "c+", build((short)  4)));
		save(new KeySignature ((short)  5, "B" , "g+", build((short)  5)));
		save(new KeySignature ((short)  6, "F+", "d+", build((short)  6)));
		save(new KeySignature ((short)  7, "C+", "a+", build((short)  7)));
	}

	private List<NoteOffset> build (short key) {
		switch (key) {
		case -7: return build ( 1,-1, 1, 0, 2,-1, 2, 0, 3, 0, 4,-1, 4, 0, 5,-1, 5, 0, 6,-1, 6, 0, 7, 0);
		case -6: return build ( 1,-1, 1, 0, 2,-1, 2, 0, 3,-1, 3, 0, 4, 0, 5,-1, 5, 0, 6,-1, 6, 0, 7, 0);
		case -5: return build ( 0, 0, 1, 0, 2,-1, 2, 0, 3,-1, 3, 0, 4, 0, 5,-1, 5, 0, 6,-1, 6, 0, 7,-1);
		case -4: return build ( 0, 0, 1, 0, 2,-1, 2, 0, 3,-1, 3, 0, 4,-1, 4, 0, 5, 0, 6,-1, 6, 0, 7,-1);
		case -3: return build ( 0, 0, 1,-1, 1, 0, 2, 0, 3,-1, 3, 0, 4,-1, 4, 0, 5, 0, 6,-1, 6, 0, 7,-1);
		case -2: return build ( 0, 0, 1,-1, 1, 0, 2, 0, 3,-1, 3, 0, 4,-1, 4, 0, 5,-1, 5, 0, 6, 0, 7,-1);
		case -1: return build ( 0, 0, 1,-1, 1, 0, 2,-1, 2, 0, 3, 0, 4,-1, 4, 0, 5,-1, 5, 0, 6, 0, 7,-1);
		//                      C           D           E     F           G           A           B
		case  0: return build ( 0, 0, 0, 1, 1, 0, 1, 1, 2, 0, 3, 0, 3, 1, 4, 0, 4, 1, 5, 0, 5, 1, 6, 0);
		case  1: return build ( 0, 0, 0, 1, 1, 0, 1, 1, 2, 0, 2, 1, 3, 0, 4, 0, 4, 1, 5, 0, 5, 1, 6, 0);
		case  2: return build (-1, 1, 0, 0, 1, 0, 1, 1, 2, 0, 2, 1, 3, 0, 4, 0, 4, 1, 5, 0, 5, 1, 6, 0);
		case  3: return build (-1, 1, 0, 0, 1, 0, 1, 1, 2, 0, 2, 1, 3, 0, 3, 1, 4, 0, 5, 0, 5, 1, 6, 0);
		case  4: return build (-1, 1, 0, 0, 0, 1, 1, 0, 2, 0, 2, 1, 3, 0, 3, 1, 4, 0, 5, 0, 5, 1, 6, 0);
		case  5: return build (-1, 1, 0, 0, 0, 1, 1, 0, 2, 0, 2, 1, 3, 0, 3, 1, 4, 0, 4, 1, 5, 0, 6, 0);
		case  6: return build (-1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 2, 0, 3, 0, 3, 1, 4, 0, 4, 1, 5, 0, 6, 0);
		case  7: return build (-1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 2, 0, 3, 0, 3, 1, 4, 0, 4, 1, 5, 0, 5, 1);
		default: return List.of();
		}

		/*
		return List.of(
			new NoteOffset((short) 0, (short) 0),
			new NoteOffset((short) 0, (short) 1),
			new NoteOffset((short) 1, (short) 0),
			new NoteOffset((short) 1, (short) 1),
			new NoteOffset((short) 2, (short) 0),
			new NoteOffset((short) 3, (short) 0),
			new NoteOffset((short) 3, (short) 1),
			new NoteOffset((short) 4, (short) 0),
			new NoteOffset((short) 4, (short) 1),
			new NoteOffset((short) 5, (short) 0),
			new NoteOffset((short) 5, (short) 1),
			new NoteOffset((short) 6, (short) 0)
		);*/
	}

	private List<NoteOffset> build (
		int o0,  int a0,
		int o1,  int a1,
		int o2,  int a2,
		int o3,  int a3,
		int o4,  int a4,
		int o5,  int a5,
		int o6,  int a6,
		int o7,  int a7,
		int o8,  int a8,
		int o9,  int a9,
		int o10, int a10,
		int o11, int a11
	) {
		return List.of(
			new NoteOffset((short) o0 , (short) a0 ),
			new NoteOffset((short) o1 , (short) a1 ),
			new NoteOffset((short) o2 , (short) a2 ),
			new NoteOffset((short) o3 , (short) a3 ),
			new NoteOffset((short) o4 , (short) a4 ),
			new NoteOffset((short) o5 , (short) a5 ),
			new NoteOffset((short) o6 , (short) a6 ),
			new NoteOffset((short) o7 , (short) a7 ),
			new NoteOffset((short) o8 , (short) a8 ),
			new NoteOffset((short) o9 , (short) a9 ),
			new NoteOffset((short) o10, (short) a10),
			new NoteOffset((short) o11, (short) a11)
		);
	}

}




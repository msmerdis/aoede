package com.aoede.transfer;

import java.util.List;

import com.aoede.modules.music.domain.Clef;
import com.aoede.modules.music.domain.KeySignature;
import com.aoede.modules.music.domain.Octave;
import com.aoede.modules.music.domain.Tempo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AoedePreload {
	private List<Clef>         clefList;
	private List<Tempo>        tempoList;
	private List<KeySignature> keysList;
	private List<Octave>       octaveList;
}




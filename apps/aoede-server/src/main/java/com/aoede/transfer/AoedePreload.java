package com.aoede.transfer;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.aoede.modules.music.domain.Clef;
import com.aoede.modules.music.domain.KeySignature;
import com.aoede.modules.music.domain.Octave;
import com.aoede.modules.music.domain.Tempo;
import com.aoede.modules.music.domain.TimeSignature;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class AoedePreload implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Clef>          clefList;
	private List<Tempo>         tempoList;
	private List<KeySignature>  keysList;
	private List<Octave>        octaveList;
	private List<TimeSignature> timeList;

	@JsonIgnore
	private LocalDate created;
}




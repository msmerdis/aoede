package com.aoede.modules.music.domain;

public enum PitchClass {
	C(0),
	D(2),
	E(4),
	F(5),
	G(7),
	A(9),
	B(11);

	public int tone;

	private PitchClass (int tone) {
		this.tone = tone;
	}
}




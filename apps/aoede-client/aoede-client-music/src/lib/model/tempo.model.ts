export interface Tempo {
	id   : string;
	desc : string;

	minTempo : number;
	stdTempo : number;
	maxTempo : number;
};

export const tempoInitializer : Tempo = {
	id   : "",
	desc : "",

	minTempo : 0,
	stdTempo : 0,
	maxTempo : 0
};

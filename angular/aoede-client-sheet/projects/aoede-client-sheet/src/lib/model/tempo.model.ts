export interface Tempo {
	id   : string;
	desc : string;

	minTempo : number;
	stdTempo : number;
	maxTempo : number;
};

export function tempoInitializer () : Tempo {
	return {
		id   : "",
		desc : "",
	
		minTempo : 0,
		stdTempo : 0,
		maxTempo : 0
	};
};

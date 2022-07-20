export interface Clef {
	id   : string;
	note : number;
	spos : number;
	type : string;
};

export function clefInitializer () : Clef {
	return {
		id   : "",
		note : 0,
		spos : 0,
		type : ""
	};
};

export interface ClefArray {
	[clef : string] : Clef;
};

export interface Clef {
	id   : string;
	note : number;
	spos : number;
	type : string;
};

export const clefInitializer : Clef = {
	id   : "",
	note : 0,
	spos : 0,
	type : ""
};

export interface KeySignature {
	id    : number;
	major : string;
	minor : string;
};

export const keySignatureInitializer : KeySignature = {
	id    : 0,
	major : "C",
	minor : "a"
};

export interface KeySignatureArray {
	[key : number] : KeySignature;
};

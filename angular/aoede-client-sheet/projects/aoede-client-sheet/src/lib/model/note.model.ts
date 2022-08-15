export interface Note {
	pitch  : number;
	value  : number;
	tags?  : string[];
	flags? : Record<string, string>;
};

export function noteInitializer () : Note {
	return {
		pitch : -1,
		value : 0
	};
};

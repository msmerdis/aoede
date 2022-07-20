import { Track } from './track.model';

export interface Sheet {
	id     : number;
	name   : string;
	tracks : Track[];
};

export function sheetInitializer () : Sheet {
	return {
		id     : 0,
		name   : "",
		tracks : []
	};
};

import { Track } from './track.model';

export interface Sheet {
	id     : number;
	name   : string;
	tracks : Track[];
};

export const sheetInitializer : Sheet = {
	id     : 0,
	name   : "",
	tracks : []
};

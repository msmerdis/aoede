import { Track } from './track.model';

export interface Sheet {
	id     : number;
	name   : string;
	tracks : Track[];
}

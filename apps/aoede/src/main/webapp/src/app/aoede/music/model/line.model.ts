import { Measure } from './measure.model';

export interface Bar {
	measure : Measure;
	width   : number;
}

export interface Line {
	bars  : Bar[];
	width : number;
}

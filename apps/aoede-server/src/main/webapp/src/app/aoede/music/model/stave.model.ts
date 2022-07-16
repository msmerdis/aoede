import { Measure } from './measure.model';

export interface Bar {
	measure : Measure;
	width   : number;
};

export interface Stave {
	bars  : Bar[];
	width : number;
};

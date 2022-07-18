import { Measure, measureInitializer } from './measure.model';

export interface Bar {
	measure : Measure;
	width   : number;
};

export const barInitializer : Bar = {
	measure : measureInitializer,
	width   : 0
};

export interface Stave {
	bars  : Bar[];
	width : number;
};

export const staveInitializer : Stave = {
	bars  : [],
	width : 0
};

import { Fraction, fractionInitializer } from './fraction.model';

export interface TimeSignature extends Fraction {
	beats? : number[];
};

export function timeSignatureInitializer (num : number = 0, den : number = 0) : TimeSignature {
	return fractionInitializer(num, den);
};

export function timeSignatureBeats (time : TimeSignature, timeList : TimeSignature[] = [], defaultTime : number[] = [0]) {
	if (time.beats !== undefined)
		return time.beats;

	let item = timeList.find(t => t.numerator == time.numerator && t.denominator == time.denominator);

	if (item !== undefined && item.beats != undefined)
		return item.beats;

	return defaultTime;
}
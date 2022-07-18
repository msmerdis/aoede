import {
	ApiError,
	ApiGeneric,
	ApiRequest,
	ApiSuccess,
	ApiFailure
} from './generic-api.model';

export interface StateData<DataType> {
	ready : boolean;
	value : DataType | null;
	error : ApiError | null;
	utime : number;
};

export const initialStateData : StateData<any> = {
	ready : false,
	value : null,
	error : null,
	utime : 0
};

export function getSuccessStateData<DataType> (data: ApiSuccess<DataType>): StateData<DataType> {
	return {
		ready : true,
		value : data.success,
		utime : data.kickoff,
		error : null
	};
};

export function getFailureStateData (data: ApiFailure): StateData<any> {
	return {
		ready : false,
		value : null,
		utime : data.kickoff,
		error : data.failure
	};
};

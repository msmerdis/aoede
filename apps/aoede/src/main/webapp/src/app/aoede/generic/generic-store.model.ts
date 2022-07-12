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

export function getGenericPayload () : ApiGeneric {
	return {
		kickoff : Date.now()
	};
};

export function getRequestPayload<DataType> (payload : DataType) : ApiRequest<DataType> {
	return {
		kickoff : Date.now(),
		payload : payload
	};
};

export function getRequestSuccess<DataType> (payload : ApiGeneric, success : DataType) : ApiSuccess<DataType> {
	return {
		kickoff : payload.kickoff,
		success : success
	};
}

export function getRequestFailure (payload : ApiGeneric, failure : ApiError) : ApiFailure {
	return {
		kickoff : payload.kickoff,
		failure : failure
	};
}

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

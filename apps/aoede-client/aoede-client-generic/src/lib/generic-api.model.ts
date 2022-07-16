export interface ApiErrorInfo {
	type : string;
	text : string;
};

export interface ApiValidation {
	name  : string;
	field : string;
	value : string;
	error : string;
};

export interface ApiError {
	code : number;
	text : string;
	desc : string;

	info?        : ApiErrorInfo[];
	validations? : ApiValidation[]
};

export interface ApiGeneric {
	kickoff : number;
};

export interface ApiRequest<DataType> extends ApiGeneric {
	payload : DataType;
}

export interface ApiSuccess<DataType> extends ApiGeneric {
	success : DataType;
};

export interface ApiFailure extends ApiGeneric {
	failure : ApiError;
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

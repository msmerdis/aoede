export interface ApiError {
	code : number;
	text : string;
	desc : string;
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

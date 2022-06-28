import { InjectionToken } from '@angular/core';

export interface UserConfig {
	apiUrl?         : string | null;
	authToken?      : string;
	renewFlag?      : string;
	tokenKeepAlive? : number;
	tokenRenew?     : number;
	tokenExpiry?    : number;
}

export const DefaultUserConfig : UserConfig = {
	apiUrl         : null,
	authToken      : "X-User-Token",
	renewFlag      : "X-User-Token-Renew",
	tokenKeepAlive :  300000, //  5 minutes x 60 seconds / minute x 1000 milliseconds / second
	tokenRenew     : 2400000, // 40 minutes x 60 seconds / minute x 1000 milliseconds / second
	tokenExpiry    : 3540000, // 59 minutes x 60 seconds / minute x 1000 milliseconds / second
}

export const UserConfigToken = new InjectionToken<UserConfig>("UserConfig");

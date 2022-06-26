import { InjectionToken } from '@angular/core';

export interface UserConfig {
	apiUrl?       : string | null;
	authToken?    : string;
	tokenRefresh? : number;
	tokenExpiry?  : number;
}

export const DefaultUserConfig : UserConfig = {
	apiUrl       : null,
	authToken    : "X-User-Token",
	tokenRefresh : 1200000, // 20 minutes x 60 seconds / minute x 1000 milliseconds / second
	tokenExpiry  : 3540000, // 59 minutes x 60 seconds / minute x 1000 milliseconds / second
}

export const UserConfigToken = new InjectionToken<UserConfig>("UserConfig");

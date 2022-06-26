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
	tokenRefresh : 180000, // 3 minutes x 60 seconds / minute x 1000 milliseconds / second
	tokenExpiry  : 240000, // 4 minutes x 60 seconds / minute x 1000 milliseconds / second
}

export const UserConfigToken = new InjectionToken<UserConfig>("UserConfig");

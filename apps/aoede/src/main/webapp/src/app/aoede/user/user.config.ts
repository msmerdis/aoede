import { InjectionToken } from '@angular/core';

export interface UserConfig {
	apiUrl?    : string | null;
	authToken? : string;
}

export const DefaultUserConfig : UserConfig = {
	apiUrl    : null,
	authToken : "X-User-Token"
}

export const UserConfigToken = new InjectionToken<UserConfig>("UserConfig");

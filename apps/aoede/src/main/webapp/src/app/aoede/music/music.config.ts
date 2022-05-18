import { InjectionToken } from '@angular/core';

export interface MusicConfig {
	apiUrl?    : string | null;
}

export const DefaultMusicConfig : MusicConfig = {
	apiUrl    : null
}

export const MusicConfigToken = new InjectionToken<MusicConfig>("MusicConfig");

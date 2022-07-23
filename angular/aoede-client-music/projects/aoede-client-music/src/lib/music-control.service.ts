import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';

import { resetMusicModule } from './store/music.actions';
import { MusicState } from './store/music.reducer';

@Injectable({
	providedIn: 'root'
})
export class MusicControlService {

	constructor(
		private store : Store<MusicState>
	) {}

	public reset () : void {
		this.store.dispatch(resetMusicModule());
	}

}

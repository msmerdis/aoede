import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';

import { resetMusicModule } from './store/music.actions';
import { getPreloadReady } from './store/music.selectors';
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

	public isReady () : Observable<boolean> {
		return this.store.select(getPreloadReady);
	}

}

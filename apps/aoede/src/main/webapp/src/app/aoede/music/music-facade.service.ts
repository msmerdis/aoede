import { Injectable } from '@angular/core';
import { Observable, pipe, take, combineLatest, throwError } from 'rxjs';
import { Store } from '@ngrx/store';

import { Sheet } from './model/sheet.model';
import { Track } from './model/track.model';
import { Clef } from './model/clef.model';
import { KeySignature } from './model/key-signature.model';
import { TrackInfo, trackInfoInitializer } from './model/track-info.model';

import { MusicState } from './store/music.reducer';
import { getClef, getKeySignature } from './store/music.selectors';

@Injectable({
	providedIn: 'root'
})
export class MusicFacadeService {

	constructor(
		private store : Store<MusicState>
	) {}

	public extractTrackInfo (sheet : Sheet, trackIdx : number) : Observable<TrackInfo> {
		if (sheet.tracks.length <= trackIdx) {
			return throwError(() => new Error('track with index ' + trackIdx + ' not found'));
		}

		var track = sheet.tracks[trackIdx];

		return combineLatest(
			this.store.select(getClef, track.clef),
			this.store.select(getKeySignature, track.keySignature),
			(clef : Clef | null, key : KeySignature | null) => {
				return {
					title         : sheet.name,
					name          : track.name != null ? track.name : "Track " + trackIdx,
					clef          : clef!!,
					tempo         : track.tempo,
					keySignature  : key!!,
					timeSignature : track.timeSignature
				};
			}
		).pipe(take(1));

	}

}

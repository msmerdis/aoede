import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable, take, filter } from 'rxjs';
import { Store } from '@ngrx/store';

import { Clef } from '../model/clef.model';
import { KeySignature } from '../model/key-signature.model';
import { Tempo } from '../model/tempo.model';

import { MusicState } from '../store/music.reducer';
import { getClefs, getKeys, getTempos } from '../store/music.selectors';

@Component({
	selector: 'aoede-music-sheet-create',
	templateUrl: './sheet-create.component.html',
	styleUrls: ['./sheet-create.component.scss']
})
export class SheetCreateComponent implements OnInit {

	minor  : boolean = false;
	custom : boolean = false;

	name  = new FormControl();
	clef  = new FormControl('Treble');
	tempo = new FormControl(120);
	key   = new FormControl(0);
	timeNum = new FormControl(4);
	timeDen = new FormControl(4);

	clefList$  : Observable<Clef[] | null>;
	keysList$  : Observable<KeySignature[] | null>;
	tempoList$ : Observable<Tempo[] | null>;

	constructor(
		private store : Store<MusicState>
	) {
		this.clefList$  = this.store.select (getClefs);
		this.keysList$  = this.store.select (getKeys);
		this.tempoList$ = this.store.select (getTempos);

		this.updateTempo ();
	}

	ngOnInit(): void {
	}

	onSubmit (): void {
		console.log("create sheet");
		console.log(" -> name  : " + this.name.value);
		console.log(" -> clef  : " + this.clef.value);
		console.log(" -> tempo : " + this.tempo.value);
		console.log(" -> key   : " + this.key.value);
		console.log(" -> time  : " + this.timeNum.value + "/" + this.timeDen.value);
	}

	updateTempo (): void {
		if (this.custom)
			return;

		this.tempoList$
			.pipe (
				filter((t) => t !== null),
				take(1)
			)
			.subscribe((tempos) => {
				if (tempos === null)
					return;

				var t = tempos.find((tempo) => tempo.stdTempo >= this.tempo.value!!);

				if (t === undefined)
					return;

				this.tempo.setValue (t.stdTempo);
			});
	}

}

import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';

import { Clef } from '../model/clef.model';
import { KeySignature } from '../model/key-signature.model';

import { MusicState } from '../store/music.reducer';
import { getClefsValue, getKeysValue } from '../store/music.selectors';

@Component({
	selector: 'aoede-music-sheet-create',
	templateUrl: './sheet-create.component.html',
	styleUrls: ['./sheet-create.component.scss']
})
export class SheetCreateComponent implements OnInit {

	minor : boolean = false;
	name  = new FormControl();
	clef  = new FormControl('Treble');
	tempo = new FormControl();
	key   = new FormControl(0);
	timeNum = new FormControl(4);
	timeDen = new FormControl(4);

	clefList$ : Observable<Clef[] | null>;
	keysList$ : Observable<KeySignature[] | null>;

	constructor(
		private store : Store<MusicState>
	) {
		this.clefList$ = this.store.select (getClefsValue);
		this.keysList$ = this.store.select (getKeysValue);
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
}

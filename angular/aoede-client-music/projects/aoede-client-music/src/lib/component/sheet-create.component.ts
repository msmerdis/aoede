import { Component, OnInit, ViewChildren, ElementRef } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable, take, filter } from 'rxjs';
import { Store } from '@ngrx/store';

import {
	Clef,
	KeySignature,
	TimeSignature,
	Tempo
} from 'aoede-client-sheet';

import { MusicState } from '../store/music.reducer';
import { getClefs, getKeys, getTempos, getTimes } from '../store/music.selectors';

@Component({
	selector: 'aoede-music-sheet-create',
	templateUrl: './sheet-create.component.html',
	styleUrls: ['./sheet-create.component.scss']
})
export class SheetCreateComponent implements OnInit {

	minor       : boolean = false;
	customTempo : boolean = false;
	customTime  : boolean = false;

	name  = new FormControl();
	clef  = new FormControl('Treble');
	tempo = new FormControl(120);
	key   = new FormControl(0);
	timeNum = new FormControl(4);
	timeDen = new FormControl(4);

	@ViewChildren('beat') beats : ElementRef[] = [];

	clefList$  : Observable<Clef[] | null>;
	keysList$  : Observable<KeySignature[] | null>;
	timesList$ : Observable<TimeSignature[] | null>;
	tempoList$ : Observable<Tempo[] | null>;

	constructor(
		private store : Store<MusicState>
	) {
		this.clefList$  = this.store.select (getClefs);
		this.keysList$  = this.store.select (getKeys);
		this.timesList$ = this.store.select (getTimes);
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
		console.log(" -> beats : " + this.extractBeats());
	}

	generateBeats (num : number) : number[] {
		return [...Array(+num).keys()];
	}

	extractBeats () : number[] | null {
		return this.beats
			.map    (beat => beat.nativeElement)
			.filter (beat => beat.checked)
			.map    (beat => beat.value);
	}

	convertBeats (beats : number[], length : number) : string {
		let rtn : string = " ";

		for (let i = 0; i < length; i += 1) {
			rtn += beats.includes(i) ? (i+1) : "-";
			rtn += " ";
		}

		return rtn;
	}

	updateTempo (): void {
		if (this.customTempo)
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

	updateTime (index : number) : void {
		this.timesList$.pipe (take(1)).subscribe(
			(times) => {
				if (times) {
					this.timeNum.setValue(times[index].numerator);
					this.timeDen.setValue(times[index].denominator);
				}
			}
		);
	}
}

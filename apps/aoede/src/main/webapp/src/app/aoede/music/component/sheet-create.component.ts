import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
	selector: 'aoede-music-sheet-create',
	templateUrl: './sheet-create.component.html',
	styleUrls: ['./sheet-create.component.scss']
})
export class SheetCreateComponent implements OnInit {

	name  = new FormControl('name');
	clef  = new FormControl('clef');
	tempo = new FormControl('tempo');
	key   = new FormControl('key');
	time  = new FormControl('time');

	constructor() { }

	ngOnInit(): void {
	}

	onSubmit (): void {
		alert("submitting form");
	}
}

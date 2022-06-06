import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
	selector: 'aoede-music-sheet-create',
	templateUrl: './sheet-create.component.html',
	styleUrls: ['./sheet-create.component.scss']
})
export class SheetCreateComponent implements OnInit {

	title = new FormControl('title');

	constructor() { }

	ngOnInit(): void {
	}

	onSubmit (): void {
		alert("submitting form");
	}
}

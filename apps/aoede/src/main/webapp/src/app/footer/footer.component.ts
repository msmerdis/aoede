import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';

import { MusicState } from '../aoede/music/store/music.reducer';
import { getMusicState } from '../aoede/music/store/music.selectors';

@Component({
	selector: 'app-footer',
	templateUrl: './footer.component.html',
	styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

	data$ : Observable<MusicState>;

	constructor(
		private store : Store<MusicState>
	) {
		this.data$ = this.store.select (getMusicState);
	}

	ngOnInit(): void {
	}

}

import { Injectable } from '@angular/core';

import { HttpClient, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

import { Sheet } from './model/sheet.model';
import { Clef } from './model/clef.model';
import { KeySignature } from './model/key-signature.model';

@Injectable({
	providedIn: 'root'
})
export class MusicService {

	constructor(
		private httpClient: HttpClient
	) {}

	private sheetUrl : string = '/api/sheet';
	private clefUrl  : string = '/api/clef';
	private keysUrl  : string = '/api/key_signature';

	public getSheetList(): Observable<Sheet[]> {
		return this.httpClient.get<Sheet[]>(this.sheetUrl);
	}

	public getSheet(id: number): Observable<Sheet> {
		return this.httpClient.get<Sheet>(this.sheetUrl + "/" + id);
	}

	public getClefList(): Observable<Clef[]> {
		return this.httpClient.get<Clef[]>(this.clefUrl);
	}

	public getKeysList(): Observable<KeySignature[]> {
		return this.httpClient.get<KeySignature[]>(this.keysUrl);
	}

}

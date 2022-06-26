import { Injectable } from '@angular/core';

import { HttpClient, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

import { Sheet } from './model/sheet.model';

@Injectable({
	providedIn: 'root'
})
export class MusicService {

	constructor(
		private httpClient: HttpClient
	) {}

	private sheetUrl : string = '/api/sheet';

	public getSheetList(): Observable<Sheet[]> {
		return this.httpClient.get<Sheet[]>(this.sheetUrl);
	}

	public getSheet(id: number): Observable<Sheet> {
		return this.httpClient.get<Sheet>(this.sheetUrl + "/" + id);
	}

}

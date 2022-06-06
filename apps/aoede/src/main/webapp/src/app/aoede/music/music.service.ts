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

	public getSheetList(): Observable<HttpResponse<Sheet[]>> {
		return this.httpClient.get<Sheet[]>(this.sheetUrl, { observe: 'response' });
	}

	public getSheet(id: number): Observable<HttpResponse<Sheet>> {
		return this.httpClient.get<Sheet>(this.sheetUrl + id, { observe: 'response' });
	}

}

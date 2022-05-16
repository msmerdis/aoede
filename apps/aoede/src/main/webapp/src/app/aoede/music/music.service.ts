import { Injectable } from '@angular/core';

import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

import { SheetCreate } from './model/sheet-create.model';

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
		return this.httpClient.get<Sheet>(this.sheetUrl + id);
	}

	public createSheet(createSheet: CreateSheet): Observable<Sheet> {
		return this.httpClient.post<Sheet>(this.sheetUrl, createSheet);
	}
}

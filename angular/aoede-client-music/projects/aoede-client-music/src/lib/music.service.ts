import { Injectable } from '@angular/core';

import { HttpClient, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

import { Sheet } from 'aoede-client-sheet';
import { Preload } from './model/preload.model';
import { GenerateSheet } from './model/generate-sheet.model';

@Injectable({
	providedIn: 'root'
})
export class MusicService {

	constructor(
		private httpClient: HttpClient
	) {}

	private sheetUrl    : string = '/api/sheet';
	private generateUrl : string = '/api/sheet/generate';
	private preloadUrl  : string = '/api/aoede/preload';

	public getSheetList(): Observable<Sheet[]> {
		return this.httpClient.get<Sheet[]>(this.sheetUrl);
	}

	public getSheet(id: number): Observable<Sheet> {
		return this.httpClient.get<Sheet>(this.sheetUrl + "/" + id);
	}

	public getPreload(): Observable<Preload> {
		return this.httpClient.get<Preload>(this.preloadUrl);
	}

	public generateSheet (data : GenerateSheet): Observable<Sheet> {
		return this.httpClient.post<Sheet>(this.generateUrl, data);
	}

}

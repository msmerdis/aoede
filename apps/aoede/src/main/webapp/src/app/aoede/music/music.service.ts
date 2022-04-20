import { Injectable } from '@angular/core';

import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class MusicService {

	constructor(
		private httpClient: HttpClient
	) {}

	getSheetList(): Observable<Sheet[]> {
		return this.httpClient.get<Sheet[]>('/api/sheet');
	}

	getSheet(id: number): Observable<Sheet> {
		return this.httpClient.get<Sheet[]>('/api/sheet/' + id);
	}
}

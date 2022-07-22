import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class ApiInterceptor implements HttpInterceptor {
	private baseUrl = 'http://localhost:8088';

	intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
		if (location.origin !== 'http://localhost:4200') {
			return next.handle(req);
		}

		const apiReq = req.clone({
			url: `${this.baseUrl}${req.url}`,
			headers: req.headers
				.set('Access-Control-Allow-Origin', '*')
				.set('Content-Type', 'application/json')
		});
		return next.handle(apiReq);
	}
}


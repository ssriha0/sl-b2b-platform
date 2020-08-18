import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpResponse,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from './../../../environments/environment';
import { map } from 'rxjs/operators';
import { ErrorService } from './error.service';

@Injectable({ providedIn: 'root' })
export class AuthInterceptorService implements HttpInterceptor {
  constructor(private errorService: ErrorService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // add authorization header with basic token if available
    request = request.clone({
      setHeaders: {
        'Content-Type': 'application/json',
        Authorization: `Basic ${btoa(
          environment.AUTH.KEY + ':' + environment.AUTH.SECRET
        )}`
      },
      withCredentials: true
    });

    return next.handle(request).pipe(
      map((event: HttpEvent<any>) => {
        if (
          event instanceof HttpResponse &&
          event.body.surveyResponse &&
          event.body.surveyResponse.results &&
          event.body.surveyResponse.results.error &&
          event.body.surveyResponse.results.error.code > 0
        ) {
          this.errorService.notify(
            event.body.surveyResponse.results.error.message
          );
        }
        return event;
      })
    );
  }
}

import { Injectable, Inject } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { DOCUMENT } from '@angular/platform-browser'

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { LoggerUtil } from '../util/logger.util';

@Injectable()
export class HttpRestAPIService {

    constructor(private _http: Http, @Inject(DOCUMENT) private document: any, private _logger: LoggerUtil) {
    }

    getDocumentObj(): string {
        return this.document;
    }

    ping(): void {
        console.log("HttpRestAPIService : ping...received");
    }

    postRequest(url: string, body: any): Observable<any> {

        let requestOptions: RequestOptions = new RequestOptions({ headers: this.getNewCommonHeaders() });

        return this._http.post(url, JSON.stringify(body), requestOptions)
            .map(this.extractData).catch(this.handleError);

    }
    saveZipCoverageRadius(url: string, body: any): Observable<any> {

        let requestOptions: RequestOptions = new RequestOptions({ headers: this.getNewCommonHeaders() });

        return this._http.post(url, body, requestOptions)
            .map(this.extractData).catch(this.handleError);

    }
    deleteCoverageArea(url: string, body: any): Observable<any> {

        let requestOptions: RequestOptions = new RequestOptions({ headers: this.getNewCommonHeaders() });

        return this._http.post(url, body, requestOptions)
            .map(this.extractData).catch(this.handleError);

    }

    getRequest(url: string): Observable<any> {

        let requestOptions: RequestOptions = new RequestOptions({ headers: this.getNewCommonHeaders() });

        return this._http.get(url, requestOptions)
            .map(this.extractData).catch(this.handleError);
    }
   
    private getNewCommonHeaders(): Headers {
        let headers = new Headers();
        return this.getCommonHeaders(headers);
    }

    private getCommonHeaders(prefilledHeaders: Headers): Headers {
        if (null != prefilledHeaders) {
            prefilledHeaders.append("Content-Type", "application/json");
            prefilledHeaders.append('Accept', 'application/json');
        }

        return prefilledHeaders;
    }

    private extractData(res: Response) {
        let body = res.json();
        return body || {};
    }

    private handleError(error: Response | any) {
        let errMsg: string;
        if (error instanceof Response) {
            const body = error.json() || {};
            const err = body.error || JSON.stringify(body);
            errMsg = `${error.status} - ${error.statusText || '{}'} ${err}`;
        } else {
            errMsg = error.message ? error.message : error.toString();
        }
        return Observable.throw(errMsg);
    }
}
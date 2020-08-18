import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response, RequestMethod } from '@angular/http'

import { LoggerUtil } from '../common/util/logger';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

@Injectable()
export class HttpRestAPIService {


    constructor(private _http: Http, private _logger: LoggerUtil) {

    }

    ping(): void {
        console.log("HttpRestAPIService : ping...received");
    }

    postRequest(url: string, body: any): Observable<any> {

        let requestOptions: RequestOptions = new RequestOptions({ headers: this.getFilledCommonHeaders() });
        requestOptions.method = RequestMethod.Post;
        requestOptions.body = body;

        this._logger.log('url: ', url, " requestOptions: ", requestOptions);

        return this._http.post(url, body, requestOptions).map(this.extractData).catch(this.handleError);
    }

    getRequest(url: string): Observable<any> {

        let requestOptions: RequestOptions = new RequestOptions({ headers: this.getFilledCommonHeaders() });

        return this._http.get(url, requestOptions).map(this.extractData).catch(this.handleError);
    }

    private getFilledCommonHeaders(): Headers {
        let headers = new Headers();
        headers.append("Content-Type", "application/json");
        return headers;
    }

    private extractData(res: Response) {
        let body = res.json();
        return body || {};
    }

    private handleError(error: Response | any) {
        let errMsg: string;
        if (error instanceof Response) {
            const body = error.json() || '';
            const err = body.error || JSON.stringify(body);
            errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
        } else {
            errMsg = error.message ? error.message : error.toString();
        }
        console.error("Error: " + errMsg);
        return Observable.throw(errMsg);
    }
}
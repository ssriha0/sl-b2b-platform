import {Injectable} from '@angular/core';

import {DropDownZipCodes} from '../modal/provider-coverage/dropdown.zipcodes';
import {CoverageAPIZipCodes} from '../modal/provider-coverage/coverage.apizipcodes';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import { Observable }     from 'rxjs/Observable';




@Injectable()
export class ProviderZIPCodeCoverageService {
  selectedZIPCodes = new Array<string>();

  constructor(private http: Http) {

  }
  searchZipCodesByZipRange(zipcode: string, coverageRadius: Number, locId: string): Observable<any> {
    console.log("zipcode and coverage,locid:");
    //let body=JSON.stringify({zip:zipcode, locId : locId, coverageRadius: distance});
    console.log(zipcode);
    console.log(coverageRadius);
    console.log(locId);
    return this.http.get('MarketFrontend/providerPortalAction_findZipCodesInRadius.action?zip=' + zipcode + '&minimumradius=0&coverageRadius=' + coverageRadius + '&locId=' + locId)
      .map(this.extractData)
      .catch(this.handleError);
  }
 
  public saveSeletedZIPCodes(zipcodesData: any, locId: string, coverageRadius: number): Observable<any> {
    let headers = new Headers({ 'Content-Type': 'application/json' });

    //let options = new RequestOptions({ headers: header });
    let body = JSON.stringify({ zipCodesData: zipcodesData, vendorLocId: locId, coverageRadius: coverageRadius });
    console.log("json post data")
    return this.http.post('MarketFrontend/providerPortalAction_updateCoverageZipCodes.action', body, { headers: headers })
      .map(this.extractData)
      .catch(this.handleError);
  }


  private extractData(res: Response) {
    let body = res.json();
    return body || {};
  }
  private extractDataZip(res: Response) {
    let body = res.json();
    console.log(body);
    return body || {};
  }

  private handleError(error: Response | any) {
    // In a real world app, we might use a remote logging infrastructure
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Observable.throw(errMsg);
  }
  //
}
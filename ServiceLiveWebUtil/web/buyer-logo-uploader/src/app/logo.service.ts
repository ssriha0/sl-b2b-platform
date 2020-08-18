import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpResponse,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { FirmLogo } from './models';
import { Ng2PicaService } from 'ng2-pica';

const baseUrl = 'MarketFrontend/buyerSurvey';
const uploadFirmLogo = '_uploadBuyerLogo.action';

@Injectable({
  providedIn: 'root'
})
export class LogoService {
  constructor(private http: HttpClient, private ng2PicaService: Ng2PicaService) {}

  uploadFirmLogo(formData: string[], file: any): Observable<any> {
    const body: FirmLogo = new FirmLogo();
    body.file = file;
    body.formData = formData;
    const uploadFirmLogoUrl: string = baseUrl + uploadFirmLogo;
    return this.postRequest(uploadFirmLogoUrl, body);
  }

  postRequest(url: string, body: any): Observable<any> {
    const requestOptions = { headers: this.getNewCommonHeaders() };

    return this.http.post(url, JSON.stringify(body), requestOptions).pipe(
      map(this.extractData),
      catchError(this.handleError)
    );
  }

  private getNewCommonHeaders(): HttpHeaders {
    const headers = new HttpHeaders();
    return this.getCommonHeaders(headers);
  }

  private getCommonHeaders(prefilledHeaders: HttpHeaders): HttpHeaders {
    if (null != prefilledHeaders) {
      prefilledHeaders.append('Content-Type', 'application/json');
      prefilledHeaders.append('Accept', 'application/json');
    }

    return prefilledHeaders;
  }

  private extractData(res: Response) {
    const body = res;
    return body || {};
  }

  private handleError(error: Response | any) {
    let errMsg = '';
    if (error instanceof Response) {
      const body = error || {};
      const err = body['error'] || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || '{}'} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    return Observable.throw(errMsg);
  }

  resizeGIF(file: File, maxWidth: number, maxHeight: number): Observable<any> {
    const img = new Image();
    const resizedFileSubject = new Subject();
    const self = this;
    img.onload = () => {
      window.URL.revokeObjectURL(img.src);
      const currentWidth = img.width;
      let currentHeight = img.height;
      let newWidth = currentWidth;
      let newHeight = currentHeight;
      if (newWidth > maxWidth) {
          newWidth = maxWidth;
          // resize height proportionally
          const ratio = maxWidth / currentWidth; // is gonna be <1
          newHeight = newHeight * ratio;
      }
      currentHeight = newHeight;
      if (newHeight > maxHeight) {
          newHeight = maxHeight;
          // resize width proportionally
          const ratio = maxHeight / currentHeight; // is gonna be <1
          newWidth = newWidth * ratio;
      }
      if (newHeight === img.height && newWidth === img.width) {
          // no resizing necessary
          resizedFileSubject.next(file);
      } else {
          self.ng2PicaService.resize([file], newWidth, newHeight).subscribe((result) => {
              // all good, result is a file
              resizedFileSubject.next(result);
          }, (error) => {
              // something went wrong
              resizedFileSubject.error({ resizedFile: file, reason: error, error: 'PICA_ERROR' });
          });
      }
    };
    img.src = window.URL.createObjectURL(file);
    return resizedFileSubject.asObservable();
  }

}

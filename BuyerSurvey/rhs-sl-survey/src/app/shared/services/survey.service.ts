import { environment } from './../../../environments/environment';
import { ISurvey } from './../../data/ISurvey';
import { IOption } from './../../data/IOption';
import { Injectable, Injector } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse
} from '@angular/common/http';
import { tap, catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { IValidateResponse } from '../../data/IValidateResponse';


@Injectable({
  providedIn: 'root'
})
export class SurveyService {
  private _soID = '';
  private _surveyType = '';
  private _csatRatings: number;
  private _npsRatings: number = null;
  private _csatOptsSelected: Array<number> = [];
  private _csatOptions: Array<IOption> = [];
  private detailsUrl = environment.API_URL + 'getDetails/';
  private validateUrl = environment.API_URL + 'validate/';
  private postUrl = environment.API_URL + 'save';
  // private detailsUrl = 'api/getDetails/';
  // private validateUrl = 'api/validate';
  private soDetailsUrl = 'http://10.3.9.66:8380/public/v1.4/buyers/3333/serviceorders/571-1921-1470-15?responseFilter=General';
    csatPrioritized = true;

  get soId(): string {
    return this._soID;
  }

  set soId(soID: string) {
    this._soID = soID;
  }

  get surveyType(): string {
    return this._surveyType;
  }

  set surveyType(surveyType: string) {
    this._surveyType = surveyType;
    this.csatPrioritized =
      this.surveyType.toUpperCase() === environment.SURVEY_TYPES.CSAT ||
      this.surveyType.toUpperCase() === environment.SURVEY_TYPES.CSAT_NPS;
  }

  get ratings(): number {
    return this.csatPrioritized ? this.csatRatings : this.npsRatings;
  }

  set ratings(ratings: number) {
    if (this.csatPrioritized) {
      this.csatRatings = ratings;
    } else {
      this.npsRatings = ratings;
    }
  }

  get csatRatings(): number {
    return this._csatRatings;
  }

  set csatRatings(csatRatings: number) {
    this._csatRatings = csatRatings;
  }

  get npsRatings(): number {
    return this._npsRatings;
  }

  set npsRatings(npsRatings: number) {
    this._npsRatings = npsRatings;
  }

  get csatOptions(): Array<IOption> {
    return this._csatOptions;
  }

  set csatOptions(csatOptions: Array<IOption>) {
    this._csatOptions = csatOptions;
  }

  get csatOptsSelected(): Array<number> {
    return this._csatOptsSelected;
  }

  constructor(
    private http: HttpClient,
    private injector: Injector
  ) {}

  doValidate(soId: String): Observable<any> {
    // const header = new HttpHeaders({
    //   'Authorization': 'Basic ' + window.btoa(environment.AUTH.KEY + ':' + environment.AUTH.SECRET)
    // });
    // const httpOptions = { headers : header, withCredentials: true,  reportProgress: true };
    // const httpOptions = {};
    return this.http.get(this.validateUrl + '?key=' + soId);
  }

  getDetails(soID): Observable<any> {
    return this.http
      .get(this.detailsUrl + '?key=' + soID)
      .pipe(
        tap(
          data => {
            if (data['surveyQuestionnaireDetail']['csat']) {
              this.csatOptions = Array<IOption>(data['surveyQuestionnaireDetail']['csat']['options']);
            }
          }
        )
      );
  }

  doPost(surveyObject: ISurvey): Observable<Object> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http
      .post<ISurvey>(this.postUrl, surveyObject, httpOptions)
      .pipe(catchError(this.handlePostError));
  }

  testSODetails(): Observable<any> {
   const httpOptions = {
      // headers: new HttpHeaders({
      //   // 'Content-Type': 'text/xml',
      //   // 'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,PATCH,OPTIONS',
      //   // 'Access-Control-Allow-Origin': '*',
      //   // 'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Access-Control-Allow-Origin, Access-Control-Request-Method',

      //   // 'cache-control': 'no-cache',
      //   // 'Authorization': 'Basic ' + btoa('4ea7bf2a68d7aef22cf427421aaae9e9:6f1e2664fac6e88efd750548d98c35be')
      // }),
      responseType: 'text'
      // withCredentials: true
    };
    // const httpOptions = { withCredentials: true };
    // const header = new HttpHeaders().set(
    //   'Authorization',
    //   'Basic ' + window.btoa('4ea7bf2a68d7aef22cf427421aaae9e9:6f1e2664fac6e88efd750548d98c35be')
    // );
    // const httpOptions = { headers : header, withCredentials: true };
    return this.http.get(this.soDetailsUrl, httpOptions as any);
  }

  setCsatOptsSelected(csatOptSelected: IOption, isSelected: boolean) {
    if (isSelected) {
      this._csatOptsSelected.push(csatOptSelected.id);
    } else {
      const index = this._csatOptsSelected.indexOf(csatOptSelected.id);
      if (index > -1) {
        this._csatOptsSelected.splice(index, 1);
      }
    }
  }

  private handlePostError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('[Survey POST] An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` + `body was: ${error.error}`
      );
    }
    // return an observable with a user-facing error message
    return throwError(
      '[Survey POST] Something bad happened; please try again later.'
    );
  }

  // prepareData(obj: any) {
  //   const initialSurveyObject = <ISurvey>{
  //     so_id: this.soId,
  //     comments: obj.comments,
  //     agreed: obj.agreed,
  //     completed: true
  //   };

  //   if (this.csatPrioritized) {
  //     initialSurveyObject.CSAT = {
  //       ratings: this.ratings,
  //       options: obj.optsSelected
  //     };
  //   } else {
  //     initialSurveyObject.NPS = { ratings: this.ratings };
  //   }

  //   return initialSurveyObject;
  // }
}

import { ErrorService } from './shared/services/error.service';
import { environment } from './../environments/environment';
import { ISurvey } from './data/ISurvey';
import { SurveyService } from './shared/services/survey.service';
import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { map } from 'rxjs/operators';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styles: []
})
export class AppComponent implements OnInit, OnDestroy {
  private buyerDetails;
  private surveyDetails;
  validUrl = false;
  isSubmitted = false;
  isAlreadySubmitted = false;
  surveyTypes = environment.SURVEY_TYPES;
  errorMessage: String = '';
  showError = false;
  showSurvey = true;
  ratings = 0;
  private initializeSubscription: Subscription;
  private errorSubscription: Subscription;
  get surveyType(): string {
    return this.service.surveyType;
  }

  set surveyType(surveyType: string) {
    this.service.surveyType = surveyType;
  }

  constructor(
    private service: SurveyService,
    private route: ActivatedRoute,
    private errorService: ErrorService,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.initializeSubscription = this.route.queryParamMap.subscribe(
      (params: Params) => {
        const paramsObject = params.params;
        if (
          paramsObject['key'] &&
          // paramsObject['surveyType'] &&
          paramsObject['ratings']
        ) {
          this.validUrl = true;
          this.loadParamData(paramsObject);
          this.loadData();
        }
      }
    );

    this.errorSubscription = this.errorService.notification$.subscribe(
      message => {
        this.errorMessage = message;
        this.showError = true;
        if (message) {
          this.showSurvey = false;
        }
        this.cd.detectChanges();
      }
    );
  }

  loadParamData(paramsObject: Params) {
    this.service.soId = encodeURIComponent(paramsObject['key']);
    // this.service.surveyType = paramsObject['surveyType'];
    this.ratings = +paramsObject['ratings'];
  }

  async loadData() {
    await this.service
      .doValidate(this.service.soId)
      .subscribe(
        data => {
          this.isSubmitted = this.isAlreadySubmitted =
            data.surveyValidation.isSubmitted;
          if (!this.isSubmitted) {
             this.initialize();
          }
        }
      );
  }

  initialize() {
    this.service.getDetails(this.service.soId).subscribe(data => {
      this.surveyDetails = data.surveyQuestionnaireDetail;
      this.buyerDetails = this.surveyDetails.buyerDetails;
      this.surveyType = this.surveyDetails.surveyType;
      this.service.ratings = this.ratings;
      this.initialSave();
    });
  }

  initialSave() {
    const initialSurveyObject: ISurvey = <ISurvey>{
      surveyRequest: {
        key: this.service.soId,
        // surveyType: this.service.surveyType.toUpperCase(),
        submit: false,
        agreed: false
      }
    };

    if (this.service.csatPrioritized) {
      initialSurveyObject.surveyRequest.CSAT = { rating: this.service.ratings, options: { optionID: [] }, comments: '' };
    } else {
      initialSurveyObject.surveyRequest.NPS = { rating: this.service.ratings, comments: '' };
    }

    this.service.doPost(initialSurveyObject).subscribe(
      data => console.log('Survey POST on Initial loading', data)
    );
  }

  formSubmit(form: ISurvey) {
    // form.surveyRequest.surveyType = this.service.surveyType.toUpperCase();
    // console.log(form);
    // this.isSubmitted = true;
    this.service
      .doValidate(this.service.soId)
      .subscribe(
        data => {
          this.isSubmitted = this.isAlreadySubmitted =
            data.surveyValidation.isSubmitted;
          if (!this.isSubmitted) {
            const requestData: ISurvey = this.sanitizeData(form);
            // console.log('Data to be submitted --> ', JSON.stringify(requestData));
            this.service.doPost(requestData).subscribe(
              data => {
                console.log('Survey POST after submission', data);
                this.isSubmitted = true;
              }
            );
          }
        }
      );

  }

  private sanitizeData(form: ISurvey): ISurvey {
    form.surveyRequest.submit = true;
    form.surveyRequest.key = this.service.soId;
    // Remove the corresponding CSAT/NPS section based on the ratings provided
    if (form.surveyRequest.CSAT && !form.surveyRequest.CSAT.rating) {
      delete form.surveyRequest.CSAT;
    }

    if (form.surveyRequest.NPS && form.surveyRequest.NPS.rating === null) {
      delete form.surveyRequest.NPS;
    }

    return form;
  }

  ngOnDestroy() {
    this.initializeSubscription.unsubscribe();
    this.errorSubscription.unsubscribe();
  }
}

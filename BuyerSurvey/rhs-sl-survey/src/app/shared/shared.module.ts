import {
  ModuleWithProviders,
  NgModule,
  Optional,
  SkipSelf,
  ErrorHandler
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CsatRatingComponent } from './csat-rating/csat-rating.component';
import { NpsRatingComponent } from './nps-rating/nps-rating.component';
import { SurveyOptionComponent } from './survey-options/survey-option/survey-option.component';
import { SurveyOptionsComponent } from './survey-options/survey-options.component';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpClientInMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService } from './services/in-memory-data.service';

import { ReactiveFormsModule } from '@angular/forms';
import { SurveyHeaderComponent } from './survey-header/survey-header.component';
import { CsatFormComponent } from './csat-form/csat-form.component';
import { SurveyFooterComponent } from './survey-footer/survey-footer.component';
import { NpsFormComponent } from './nps-form/nps-form.component';

import { LoaderComponent } from './loader/loader.component';
import { LoaderInterceptorService } from './loader/services/loader-interceptor.service';

import { AuthInterceptorService } from './services/auth-interceptor.service';
import { ErrorsHandler } from './services/errors-handler';

@NgModule({
  declarations: [
    CsatRatingComponent,
    NpsRatingComponent,
    SurveyOptionComponent,
    SurveyOptionsComponent,
    SurveyHeaderComponent,
    CsatFormComponent,
    SurveyFooterComponent,
    NpsFormComponent,
    LoaderComponent
  ],
  imports: [
    CommonModule,
    NgbModule,
    HttpClientModule,
    ReactiveFormsModule,

    // The HttpClientInMemoryWebApiModule module intercepts HTTP requests
    // and returns simulated server responses.
    // Remove it when a real server is ready to receive requests.
    HttpClientInMemoryWebApiModule.forRoot(InMemoryDataService, {
      passThruUnknownUrl: true
    })
  ],
  // providers: [
  //   { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true }
  // ],
  exports: [
    ReactiveFormsModule,
    CsatRatingComponent,
    NpsRatingComponent,
    SurveyOptionsComponent,
    SurveyHeaderComponent,
    SurveyFooterComponent,
    CsatFormComponent,
    NpsFormComponent,
    LoaderComponent
  ]
})
export class SharedModule {
  constructor(@Optional() @SkipSelf() parentModule: SharedModule) {
    if (parentModule) {
      throw new Error(
        'SharedModule is already loaded. Import it in the AppModule only'
      );
    }
  }

  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SharedModule,
      providers: [
        {
          provide: ErrorHandler,
          useClass: ErrorsHandler
        },
        {
          provide: HTTP_INTERCEPTORS,
          useClass: LoaderInterceptorService,
          multi: true
        },
        {
          provide: HTTP_INTERCEPTORS,
          useClass: AuthInterceptorService,
          multi: true
        }
      ]
    };
  }
}

import { SurveyService } from './../shared/services/survey.service';
import { Component, Output, Input, EventEmitter } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ISurvey } from './../data/ISurvey';

@Component({
  selector: 'app-nps-prioritized',
  templateUrl: './nps-prioritized.component.html',
  styles: []
})
export class NpsPrioritizedComponent {
  @Input() buyerDetails;
  @Input() surveyDetails;
  @Output() doPost = new EventEmitter();

  surveyForm = new FormGroup({
    NPS: new FormGroup({
      rating: new FormControl(0),
      comments: new FormControl(''),
    }),
    CSAT: new FormGroup({
      rating: new FormControl(0),
      options: new FormGroup({
        optionID: new FormControl([])
      }),
      comments: new FormControl(''),
    }),
    agreed: new FormControl(false),
  });

  constructor(private surveyService: SurveyService) {}

  onSubmit() {
    this.surveyForm.patchValue({
      NPS: { rating: this.surveyService.npsRatings },
      CSAT: {
        rating: this.surveyService.csatRatings,
        options: { optionID: this.surveyService.csatOptsSelected }
      }
    });
    // TODO: Use EventEmitter with form value
    console.warn(this.surveyForm.value);
    this.doPost.emit(<ISurvey> { surveyRequest: this.surveyForm.value });
  }
}

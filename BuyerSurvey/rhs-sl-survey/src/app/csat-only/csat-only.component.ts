import { ISurvey } from './../data/ISurvey';
import { SurveyService } from './../shared/services/survey.service';
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-csat-only',
  templateUrl: './csat-only.component.html',
  styles: []
})
export class CsatOnlyComponent {
  @Input() buyerDetails;
  @Input() surveyDetails;
  @Output() doPost = new EventEmitter();

  surveyForm = new FormGroup({
    CSAT: new FormGroup({
      rating: new FormControl(0),
      options: new FormGroup({
        optionID: new FormControl([])
      }),
      comments: new FormControl('')
    }),
    agreed: new FormControl(false),
  });

  constructor(private surveyService: SurveyService) {}

  onSubmit() {
    this.surveyForm.patchValue({
      CSAT: {
        rating: this.surveyService.ratings,
        options: { optionID: this.surveyService.csatOptsSelected }
      }
    });
    // TODO: Use EventEmitter with form value
    console.warn(this.surveyForm.value);
    this.doPost.emit(<ISurvey> { surveyRequest: this.surveyForm.value });
  }

}

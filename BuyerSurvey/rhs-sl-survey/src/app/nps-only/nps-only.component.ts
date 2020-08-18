import { ISurvey } from './../data/ISurvey';
import { SurveyService } from './../shared/services/survey.service';
import { Component, Output, Input, EventEmitter } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';


@Component({
  selector: 'app-nps-only',
  templateUrl: './nps-only.component.html',
  styles: []
})
export class NpsOnlyComponent {
  @Input() buyerDetails;
  @Input() surveyDetails;
  @Output() doPost = new EventEmitter();

  surveyForm = new FormGroup({
    NPS: new FormGroup({
      rating: new FormControl(0),
      comments: new FormControl('')
    }),
    agreed: new FormControl(false),
  });

  constructor(private surveyService: SurveyService) {}

  onSubmit() {
    this.surveyForm.patchValue({
      NPS: { rating: this.surveyService.npsRatings }
    });
    // TODO: Use EventEmitter with form value
    console.warn(this.surveyForm.value);
    this.doPost.emit(<ISurvey> { surveyRequest: this.surveyForm.value });
  }
}

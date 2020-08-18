import { SurveyService } from './../services/survey.service';
import { FormGroup } from '@angular/forms';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-csat-form',
  templateUrl: './csat-form.component.html',
  styles: []
})
export class CsatFormComponent implements OnInit {
  get ratings() {
    return this.surveyService.csatRatings;
  }
  @Input() parentForm: FormGroup;
  @Input() surveyDetails: any;
  @Input() selectedRatings: number;
  constructor(private surveyService: SurveyService) {}

  ngOnInit() {
    // this.surveyService
    //   .testSODetails()
    //   .subscribe(data =>
    //     this.parentForm.patchValue({ CSAT: { comments: JSON.stringify(data) } })
    //   );
  }
}

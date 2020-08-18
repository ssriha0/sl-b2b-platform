import { SurveyService } from './../services/survey.service';
import { FormGroup } from '@angular/forms';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-nps-form',
  templateUrl: './nps-form.component.html',
  styles: []
})
export class NpsFormComponent implements OnInit {
  get ratings() {
    return this.surveyService.npsRatings;
  }
  @Input() parentForm: FormGroup;
  @Input() surveyDetails: any;
  @Input() buyer: string;
  @Input() selectedRatings: number;
  question: string;
  constructor(private surveyService: SurveyService) { }

  ngOnInit() {
    this.question = this.surveyDetails.question.replace('[]', this.buyer);
  }
}

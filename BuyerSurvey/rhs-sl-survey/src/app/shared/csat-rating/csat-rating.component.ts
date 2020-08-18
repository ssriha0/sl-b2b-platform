import { Component, Input, Output } from '@angular/core';
import { EventEmitter } from 'protractor';
import { SurveyService } from '../services/survey.service';

@Component({
  selector: 'app-csat-rating',
  templateUrl: './csat-rating.component.html',
  styleUrls: ['./csat-rating.component.css']
})
export class CsatRatingComponent {
  @Input() currentRating = 0;

  constructor(private service: SurveyService) { }

  updateRatings(ratings) {
    this.service.csatRatings = ratings;
  }

}

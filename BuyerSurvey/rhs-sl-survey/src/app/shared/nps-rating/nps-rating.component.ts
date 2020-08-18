import { Component, Input } from '@angular/core';
import { SurveyService } from '../services/survey.service';

@Component({
  selector: 'app-nps-rating',
  templateUrl: './nps-rating.component.html',
  styleUrls: ['./nps-rating.component.css']
})
export class NpsRatingComponent {
  @Input() currentRating = null;
  constructor(private service: SurveyService) {}

  updateRatings(ratings) {
    if (ratings > 0) {
      this.service.npsRatings = ratings;
    } else {
      this.currentRating = null;
    }
  }

  clickZeroRating() {
    this.currentRating = 0;
    this.service.npsRatings = 0;
  }


}

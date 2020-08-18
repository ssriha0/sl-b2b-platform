import { IOption } from './../../data/IOption';
import { Component, OnInit, Input } from '@angular/core';
import { SurveyService } from '../services/survey.service';

@Component({
  selector: 'app-survey-options',
  templateUrl: './survey-options.component.html',
  styleUrls: ['./survey-options.component.css']
})
export class SurveyOptionsComponent implements OnInit {
  @Input() options = [];
  constructor(private service: SurveyService) { }

  ngOnInit() {}

  optionSelected(selectedObject: any) {
    this.service.setCsatOptsSelected(selectedObject.optSelected, selectedObject.selected);
  }

}

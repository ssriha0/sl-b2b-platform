import { Component, Input, Output, OnInit, EventEmitter } from '@angular/core';
import { IOption } from '../../../data/IOption';

@Component({
  selector: 'app-survey-option',
  templateUrl: './survey-option.component.html',
  styleUrls: ['./survey-option.component.css']
})
export class SurveyOptionComponent implements OnInit {
  @Input() isSelected = false;
  @Input() option: IOption;
  @Output() selectAction = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  selectOpt(option: IOption) {
    this.isSelected = !this.isSelected;
    this.selectAction.emit({optSelected: option, selected: this.isSelected});
  }

}

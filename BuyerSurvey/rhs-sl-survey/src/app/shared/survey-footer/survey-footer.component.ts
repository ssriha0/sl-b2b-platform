import { FormGroup } from '@angular/forms';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-survey-footer',
  templateUrl: './survey-footer.component.html',
  styles: []
})
export class SurveyFooterComponent {
  @Input() parentForm: FormGroup;
  @Input() privacyLink: String;
  @Input() termsLink: String;
  constructor() { }

}

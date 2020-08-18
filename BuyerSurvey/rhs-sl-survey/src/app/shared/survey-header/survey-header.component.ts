import { Component, OnInit, ChangeDetectionStrategy, Input } from '@angular/core';

@Component({
  selector: 'app-survey-header',
  template: `
    <div class="row">
      <div class="col-md-3 col-12">
        <img class="img-fluid logo-img" [src]="buyerDetails.logo" alt="Logo" />
      </div>
      <div class="col-md-8 col-12">
        <h1>
          Thank you for scheduling your service with {{ buyerDetails.name }}
        </h1>
      </div>
    </div>
  `,
  styles: [`
    .logo-img {
      padding: 0 5px 15px;
    }
  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SurveyHeaderComponent implements OnInit {
  @Input() buyerDetails;

  constructor() { }

  ngOnInit() {
  }

}

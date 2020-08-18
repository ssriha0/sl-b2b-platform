import { Component } from '@angular/core';

import { LoggerUtil } from './common/util/logger';

import '../assets/css/styles.less';

@Component({
  selector: 'my-app',
  templateUrl: 'app.component.html'
})
export class AppComponent { 
  constructor(private _logger : LoggerUtil) {
    this._logger.log('********************************\nInit ServiceLive calendar view\n********************************');
  }
}

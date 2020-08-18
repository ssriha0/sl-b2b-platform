import { Component, Input } from '@angular/core';


import { LoggerUtil } from '../../util/logger.util';

@Component({
    selector: 'wait-loader',
    template: require('./wait-loader.component.html'),
    styles: [require('./wait-loader.component.css')]
})
export class WaitLoaderComponent {

    @Input()
    private showLoader: boolean = false;

    @Input()
    private rateCardLoader: boolean = false;

     @Input()
    private showCoverageLoader: boolean = false;

    @Input()
    private showProfileLoader: boolean = false;

    constructor(private _logger: LoggerUtil) {
    }
    
}
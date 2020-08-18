import { Component } from '@angular/core';

@Component({
    selector: 'sl-consumer-header',
    template: require('./sl-consumer-header.component.html'),
    styles: [ require('./sl-consumer-header.component.css') ]
})
export class SLConsumerHeader {
    headerText: string = "ServiceLive Consumer Services";
}
import { Component, AfterViewInit, Input } from '@angular/core';

import {LoggerUtil} from '../../common/util/logger.util';
import { SkuDetails } from '../../common/modal/get-started/get-started.modal';
declare var jQuery: any;

@Component({
    selector: 'service-carousel',
    template: require('./service-carousel.component.html'),
    styles: [
        require('./service-carousel.component.css')
    ]
})
export class ServiceCarousel implements AfterViewInit {
    @Input()
    private skuDetails: SkuDetails;

    constructor(private _logger: LoggerUtil) {

    }

    ngAfterViewInit() {
        this._logger.log("start afterViewInit ServiceCarousel");

        jQuery('.multiple-items').slick({
            infinite: true,
            slidesToShow: 4,
            slidesToScroll: 1,
            autoplaySpeed: 2000
        });

        this._logger.log("end afterViewInit ServiceCarousel")
    }
}
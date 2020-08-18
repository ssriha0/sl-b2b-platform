import { Component, OnInit, Input } from '@angular/core';

import { LoggerUtil } from '../../common/util/logger';

@Component({
    selector: 'loader',
    template: `<div class="overlay" *ngIf="showLoader"><div class="loader"></div></div>`,
    styles: [`
        .loader, .loader:after {
        border-radius: 50%;
        width: 10em;
        height: 10em;
        }

        .loader {
        left: 53%;
        top: 270px;
        z-index: 102;
        margin: -75px 0 0 -75px;
        font-size: 10px;
        width: 80px;
        height: 80px;
        position: fixed;
        text-indent: -9999em;
        border-top: 1.1em solid white;
        border-right: 1.1em solid white;
        border-bottom: 1.1em solid white;
        border-left: 1.1em solid #286090;
        -webkit-transform: translateZ(0);
        -ms-transform: translateZ(0);
        transform: translateZ(0);
        -webkit-animation: load8 1.1s infinite linear;
        animation: load8 1.1s infinite linear;
        }

        .overlay {
            z-index: 101;
            background: lightgray;
            position: absolute;
            display: block;
            top: 0;
            right: 0;
            bottom: 0;
            left: 0;
            opacity: 0.9;
        }

        @-webkit-keyframes load8 {
        0% {
            -webkit-transform: rotate(0deg);
            transform: rotate(0deg);
        }
        100% {
            -webkit-transform: rotate(360deg);
            transform: rotate(360deg);
        }
        }

        @keyframes load8 {
        0% {
            -webkit-transform: rotate(0deg);
            transform: rotate(0deg);
        }
        100% {
            -webkit-transform: rotate(360deg);
            transform: rotate(360deg);
        }
        }
    `]
})
export class LoaderComponent implements OnInit {

    @Input()
    private showLoader: boolean = false;

    constructor(private _logger: LoggerUtil) {
    }

    ngOnInit() {

    }
}
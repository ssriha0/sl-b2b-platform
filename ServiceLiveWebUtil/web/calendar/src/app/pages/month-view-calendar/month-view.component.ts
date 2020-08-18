import { Component, OnInit, Input, OnChanges } from '@angular/core';

@Component({
    selector: 'month-view',
    templateUrl: 'month-view.component.html'
})

export class MonthViewComponent implements OnInit, OnChanges{
    @Input()
    selectedView: string;

    @Input()
    selectedDate: Date;

    constructor() { }

    ngOnInit() { }

    ngOnChanges() {
        
    }
}
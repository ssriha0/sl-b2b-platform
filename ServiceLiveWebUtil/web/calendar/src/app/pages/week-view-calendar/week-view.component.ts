import { Component, OnInit, Input, Output, EventEmitter, OnChanges } from '@angular/core';
import { addDays, startOfWeek, endOfWeek, addHours, addMinutes, getHours, startOfDay, endOfDay, format } from 'date-fns';

import { LoggerUtil } from '../../common/util/logger';
import { SOStatus } from '../../common/util/so-status';
import { getEvents } from '../../common/util/api-urls';
import { DataStorageUtil } from '../../common/util/data-storage';
import { HttpRestAPIService } from '../../services/http-rest-api.service';

import { WeekViewModal } from '../../common/modal/week-view.modal';
import { CustomCalendarEvent } from '../../common/modal/calendar-event.modal';
import { CalendarResponse, EventResponse } from '../../common/modal/calendar-response.modal';
import { Provider } from '../../common/modal/provider.modal';

@Component({
    selector: 'week-view',
    templateUrl: 'week-view.component.html'
})

export class WeekViewComponent implements OnInit, OnChanges {
    @Input()
    selectedView: string;

    @Input()
    hideWeekDaysInput: Array<number> = [];

    @Input()
    selectedDate: Date;

    @Output()
    showLoader: EventEmitter<boolean> = new EventEmitter<boolean>();

    preDate: Date;

    @Output()
    openSOActionPopup: EventEmitter<CustomCalendarEvent> = new EventEmitter<CustomCalendarEvent>();

    initDone: boolean = false;

    isCompanyView: boolean = true;

    selectedProvider: Provider;

    providerList: Array<Provider> = [];

    weekEventsUI: Array<WeekViewModal> = [];

    weekEvents: Array<WeekViewModal> = [];

    constructor(private _dataUtil: DataStorageUtil, private _logger: LoggerUtil, private _httpService: HttpRestAPIService) { }

    ngOnInit() {
        this._logger.log('week-view constructor');
        this.initDone = true;
    }

    ngOnChanges() {
        this._logger.log('week view ' + this.selectedView);
        if (!this.initDone || this.preDate.getTime() != this.selectedDate.getTime()) {
            // this.mock();
            this.showLoader.emit(true);
            this.getWeekViewData();
            this.preDate = this.selectedDate;
        } else {
            this.initUIValues();
        }
    }

    initUIValues() {
        this.isCompanyView = this.selectedView == 'company' ? true : false;
        if (!this.isCompanyView) {
            //if(!) this.selectedProvider = this.providerList[0];
            this.filterProviderEvents();
        } else {
            this.displayCompanyEvents();
        }
    }

    getWeekViewData() {
        // ('executing fetch method');
        try {
            this._httpService.postRequest(getEvents, {
                startDate: startOfDay(startOfWeek(this.selectedDate)).getTime() - (this.selectedDate.getTimezoneOffset() * 60000),
                endDate: endOfDay(endOfWeek(this.selectedDate)).getTime() - (this.selectedDate.getTimezoneOffset() * 60000)
            }).subscribe(
                data => {
                    let response: CalendarResponse = JSON.parse(data);
                    this._logger.log(response);
                    this.showLoader.emit(false);
                    if (response.error) {

                    } else {
                        this.processReply(response.result);
                        this.initUIValues();
                        this._dataUtil.setStorage(response.editable);
                    }
                },
                error => {
                    this.showLoader.emit(false);
                    // ('error while fetching records.');
                },
                () => {
                    // ('executed fetch method');
                });
        } catch (error) {
            this.showLoader.emit(false);
            // ('Error occured: ', error);
        }
    }

    filterProviderEvents() {
        this.weekEventsUI = [];
        this.weekEvents.forEach(weekEvent => {
            let tempWeekViewModal: WeekViewModal = new WeekViewModal();
            tempWeekViewModal.date = weekEvent.date;
            tempWeekViewModal.dayOfWeek = weekEvent.dayOfWeek;
            tempWeekViewModal.dateString = weekEvent.dateString;
            tempWeekViewModal.events = weekEvent.events.filter(event =>
                event.providerDetails.id == this.selectedProvider.id
            );
            this.weekEventsUI.push(tempWeekViewModal);
        });
    }

    displayCompanyEvents() {
        this.weekEventsUI = this.weekEvents;
    }

    openSOAction(event: CustomCalendarEvent) {
        this.openSOActionPopup.emit(event);
    }

    processReply(result: any) {
        this._logger.log('inside week process reply');

        this.weekEvents = [];
        for (let i in [0, 1, 2, 3, 4, 5, 6]) {
            let weekViewModal: WeekViewModal = new WeekViewModal();
            weekViewModal.dayOfWeek = parseInt(i);
            weekViewModal.date = addDays(startOfWeek(this.selectedDate), Number.parseInt(i));
            weekViewModal.dateString = format(addDays(startOfWeek(this.selectedDate), Number.parseInt(i)), 'Do');
            // this._logger.log(weekViewModal.date);
            weekViewModal.events = [];

            this.weekEvents.push(weekViewModal);
        }

        this.providerList = [];
        for (let providerId of Object.getOwnPropertyNames(result)) {
            let provider: Provider = new Provider();
            provider.id = Number.parseInt(providerId);
            this.providerList.push(provider);

            let eventDetails: Array<EventResponse> = result[providerId];
            provider.name = eventDetails[0].personDetail ? eventDetails[0].personDetail.personFirstName + " " + eventDetails[0].personDetail.personLastName : 'NA-' + providerId;
            for (let eventDetail of eventDetails) {
                if (eventDetail.type != 'BLANK') {
                    let event: CustomCalendarEvent = new CustomCalendarEvent();
                    event.providerDetails = { name: provider.name, id: provider.id, location: 'location', customer: 'cust name' };
                    event.eventName = eventDetail.eventName
                    event.id = eventDetail.eventId;
                    event.status = eventDetail.status;
                    event.type = eventDetail.type;
                    event.cssClass = eventDetail.type == 'ServiceLive' ? "internal-event" : "external-event";

                    event.start = this.getStartDateTime(eventDetail.startDate, eventDetail.startTime);
                    event.startDate = eventDetail.startDate;
                    event.startTime = eventDetail.startTime;

                    event.end = this.getEndDateTime(eventDetail.endDate, eventDetail.endTime);
                    event.endDate = eventDetail.endDate;
                    event.endTime = eventDetail.endTime;

                    event.memberCity = eventDetail.memberCity;
                    event.memberFirstName = eventDetail.memberFirstName;
                    event.memberLastName = eventDetail.memberLastName;
                    event.memberState = eventDetail.memberState;
                    event.memberZip = eventDetail.memberZip;

                    event.serviceOrder = {
                        soId: eventDetail.eventId,
                        soName: eventDetail.eventName,
                        status: Number.parseInt(eventDetail.status + ""),
                        personId: provider.id,
                        startDate: eventDetail.startDate,
                        startTime: eventDetail.startTime,
                        endDate: eventDetail.endDate,
                        endTime: eventDetail.endTime
                    };

                    this.weekEvents[new Date(eventDetail.startDate).getDay()].events.push(event);
                }
            }
        }

        this.weekEvents.forEach(
            weekViewModal => {
                weekViewModal.events = weekViewModal.events.sort(
                    (event1, event2) => {
                        return event1.start - event2.start;
                    }
                )
            }
        );

        this.weekEventsUI = this.weekEvents;

        this.selectedProvider = this.providerList[0];
    }

    getStartDateTime(date: number, time: string): number {
        let returnHour: number;

        if (date) {
            let givenDate: Date = new Date(date);
            if (startOfDay(givenDate).getTime() < startOfDay(this.selectedDate).getTime()) {
                returnHour = getHours(startOfDay(this.selectedDate));
            } else {
                returnHour = getHours(addMinutes(addHours(startOfDay(givenDate), Number.parseInt(time.split(":")[0])), Number.parseInt(time.split(":")[1])));
            }

        } else {
            returnHour = getHours(startOfDay(this.selectedDate));
        }

        // ("start date ", givenDate, time, returnHour);
        return returnHour;
    }

    getEndDateTime(date: number, time: string): number {
        let returnHour: number;

        if (date) {
            let givenDate: Date = new Date(date);
            if (endOfDay(givenDate).getTime() > endOfDay(this.selectedDate).getTime()) {
                returnHour = getHours(endOfDay(this.selectedDate)) + 1;
            } else {
                returnHour = getHours(addMinutes(addHours(startOfDay(givenDate), Number.parseInt(time.split(":")[0])), Number.parseInt(time.split(":")[1])));
            }

        } else {
            returnHour = getHours(endOfDay(this.selectedDate)) + 1;
        }

        // ("end date ", givenDate, time, returnHour);
        return returnHour;
    }

    mock() {
        const dummyProviderNames = ['Alok', 'Ajay', 'Rohit', 'Vinod'];

        this.weekEvents = [];
        for (let i in [0, 1, 2, 3, 4, 5, 6]) {
            let weekViewModal: WeekViewModal = new WeekViewModal();
            weekViewModal.dayOfWeek = parseInt(i);
            weekViewModal.date = addDays(startOfWeek(new Date()), Number.parseInt(i));
            weekViewModal.dateString = format(addDays(startOfWeek(new Date()), Number.parseInt(i)), 'Do');
            //this._logger.log(weekViewModal.date);
            weekViewModal.events = [];
            for (let j = 0; j < Number.parseInt((Math.random() * 10).toFixed(0)); j++) {
                let event: CustomCalendarEvent = new CustomCalendarEvent();
                event.title = "Event " + i + ' ' + j;
                event.providerDetails = { name: dummyProviderNames[Number.parseInt((Math.random() * 10).toFixed(0)) % 4], id: Number.parseInt((Math.random() * 10).toFixed(0)) % 4 };
                event.cssClass = (Number.parseInt((Math.random() * 10).toFixed(0)) % 2 == 0) ? "internal-event" : "external-event";
                weekViewModal.events.push(event);
            }
            this.weekEvents.push(weekViewModal);
        }
        this.weekEventsUI = this.weekEvents;

        this.providerList = [];
        for (let i in [0, 1, 2, 3]) {
            let provider: Provider = new Provider();
            provider.id = Number.parseInt(i);
            provider.name = dummyProviderNames[i];
            this.providerList.push(provider);
        }
        this.selectedProvider = this.providerList[0];
    }
}
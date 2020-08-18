import { Component, OnInit, OnChanges, Input, Output, EventEmitter } from '@angular/core';
import { addHours, addMinutes, getHours, startOfDay, endOfDay } from 'date-fns';

import { LoggerUtil } from '../../common/util/logger';
import { SOStatus } from '../../common/util/so-status';
import { getEvents } from '../../common/util/api-urls';
import { DataStorageUtil } from '../../common/util/data-storage';
import { HttpRestAPIService } from '../../services/http-rest-api.service';

import { TimeFilter, EventDetailFilter } from '../../common/modal/result-filter.model';
import { CustomCalendarEvent } from '../../common/modal/calendar-event.modal';
import { CalendarResponse, EventResponse } from '../../common/modal/calendar-response.modal';
import { Provider } from '../../common/modal/provider.modal';

@Component({
    selector: 'day-view',
    templateUrl: 'day-view.component.html'
})

export class DayViewComponent implements OnInit, OnChanges {

    @Input()
    selectedView: string;

    @Input()
    eventDetailFilter: EventDetailFilter = new EventDetailFilter();

    @Input()
    timeFilter: TimeFilter;

    @Input()
    selectedDate: Date;

    preDate: Date;

    @Output()
    openSOActionPopup: EventEmitter<CustomCalendarEvent> = new EventEmitter<CustomCalendarEvent>();

    @Output()
    openAddEventPopup: EventEmitter<any> = new EventEmitter<any>();

    initDone: boolean = false;

    isCompanyView: boolean = true;

    timeWindows: Array<number>;

    selectedProvider: number = 0;

    providers: Array<Provider> = [];
    providersUI: Array<Provider> = [];

    @Output()
    providerList: EventEmitter<Array<Provider>> = new EventEmitter<Array<Provider>>();

    @Output()
    showLoader: EventEmitter<boolean> = new EventEmitter<boolean>();

    //selectedProEvents: Array<CustomCalendarEvent> = [];

    selectedProHours: Array<number> = [];

    // proEventList: Array<Array<CustomCalendarEvent>> = [];

    proMultiEventList: Array<Array<Array<any>>> = [];
    proMultiEventListUI: Array<Array<Array<any>>> = [];
    maxResult: number = 6;

    constructor(private _dataUtil: DataStorageUtil, private _logger: LoggerUtil, private _httpService: HttpRestAPIService) { }

    ngOnInit() {
        // ('day-view ngOnInit');
        this.initDone = true;
    }

    ngOnChanges() {
        // ('day view ngOnChanges ' + this.selectedView);
        // Init time window data
        if (!this.initDone || this.preDate.getTime() != this.selectedDate.getTime()) {
            this.getDayViewData();
            this.preDate = this.selectedDate;
        } else {
            this.initUIValues();
        }
    }

    initUIValues() {
        let startTime = 0;
        let endTime = 23;

        if (this.timeFilter.start) {
            startTime = Number.parseInt(this.timeFilter.start.toString().split(":")[0]);
        }
        if (this.timeFilter.end) {
            endTime = Number.parseInt(this.timeFilter.end.toString().split(":")[0]);
        }
        this.timeWindows = [];
        for (let index = startTime; index <= endTime; index++) {
            this.timeWindows.push(index);
        }

        this.isCompanyView = this.selectedView == 'company' ? true : false;
        if (!this.isCompanyView) {
            this.selectPro();
            this.maxResult = 20;
        } else {
            this.companyData();
        }
    }

    selectPro() {
        this.providersUI = [];
        this.selectedProHours = [];
        this.proMultiEventListUI = [];

        if (this.proMultiEventList[this.selectedProvider]) {
            let eventList: Array<Array<any>> = [];
            eventList[0] = new Array<any>(this.timeWindows.length);
            for (let index = 0; index < this.timeWindows.length; index++) {
                eventList[0][index] = 0;
            }

            this.proMultiEventList[this.selectedProvider].forEach(
                data1 => {
                    data1.forEach(
                        data2 => {
                            if (data2 && data2 != 1 && data2 != 0) {
                                if (this.timeWindows.indexOf(data2.start) >= 0
                                    || this.timeWindows.indexOf(data2.end - 1) >= 0
                                    || (this.timeWindows[0] > data2.start
                                        && this.timeWindows[this.timeWindows.length - 1] < data2.end)) {
                                    this.checkAndInsertSlots(data2, eventList);
                                }
                            }
                        });
                });

            this.proMultiEventListUI[0] = eventList;
        }
        this.providersUI[0] = this.providers[this.selectedProvider];
    }

    companyData() {
        this.proMultiEventListUI = [];
        this.proMultiEventList = [];
        for (let i = 0; i < this.providers.length; i++) {

            let eventList: Array<Array<any>> = [];
            eventList[0] = new Array<any>(this.timeWindows.length);
            for (let index = 0; index < this.timeWindows.length; index++) {
                eventList[0][index] = 0;
            }

            this.providers[i].multipleEvents.forEach(
                data1 => {
                    data1.forEach(
                        data2 => {
                            if (data2 && data2 != 1 && data2 != 0) {
                                if (this.timeWindows.indexOf(data2.start) >= 0
                                    || this.timeWindows.indexOf(data2.end - 1) >= 0
                                    || (this.timeWindows[0] > data2.start
                                        && this.timeWindows[this.timeWindows.length - 1] < data2.end)) {
                                    this.checkAndInsertSlots(data2, eventList);
                                }
                            }
                        });
                });
            this.proMultiEventList.push(eventList);
        }

        this.proMultiEventListUI = this.proMultiEventList;
        this.maxResult = 6;
        this.providersUI = this.providers;
    }

    openSOAction(event: CustomCalendarEvent) {
        this.openSOActionPopup.emit(event);
    }

    openAddNewEventPopUp(event: any) {
        this.openAddEventPopup.emit(event);
    }

    getDayViewData() {
        this._logger.log('executing fetch method');
        this.showLoader.emit(true);
        try {
            this._httpService.postRequest(getEvents, {
                startDate: startOfDay(this.selectedDate).getTime() - (this.selectedDate.getTimezoneOffset() * 60000),
                endDate: endOfDay(this.selectedDate).getTime() - (this.selectedDate.getTimezoneOffset() * 60000)
            }).subscribe(
                data => {
                    this.showLoader.emit(false);
                    let response: CalendarResponse = JSON.parse(data);
                    this._logger.log(response);
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

    processReply(result: any) {
        this._logger.log('inside day process reply');
        let startTime = 0;
        let endTime = 23;
        this.timeWindows = [];
        for (let index = startTime; index <= endTime; index++) {
            this.timeWindows.push(index);
        }

        this.providers = [];
        this.providersUI = [];

        let timeZoneDiff: number = this.selectedDate.getTimezoneOffset();
        //this._logger.log(timeZoneDiff);

        for (let providerId of Object.getOwnPropertyNames(result)) {
            let provider: Provider = new Provider();
            provider.id = Number.parseInt(providerId);
            // ('1 ', provider);

            let proEvents = [];
            let eventDetails: Array<EventResponse> = result[providerId];
            provider.name = eventDetails[0].personDetail ? eventDetails[0].personDetail.personFirstName + " " + eventDetails[0].personDetail.personLastName : 'NA-' + providerId;

            let timeWindow: Array<Array<any>> = [];

            timeWindow[0] = new Array<any>(24);
            for (let index = 0; index <= 23; index++) {
                timeWindow[0][index] = 0;
            }

            for (let eventDetail of eventDetails) {
                if (eventDetail.type != 'BLANK') {
                    let event: CustomCalendarEvent = new CustomCalendarEvent();

                    event.providerDetails = { id: provider.id, name: provider.name, location: 'location', customer: 'cust name' };
                    event.eventName = eventDetail.eventName
                    event.id = eventDetail.eventId;
                    event.status = eventDetail.status;
                    event.type = eventDetail.type;
                    event.cssClass = eventDetail.type == 'ServiceLive' ? "internal-event" : "external-event";

                    event.statusString = this.getStatusString(Number.parseInt(eventDetail.status + ""));

                    event.start = this.getStartDateTime(eventDetail.startDate, eventDetail.startTime, timeZoneDiff);
                    event.startDate = eventDetail.startDate;
                    event.startTime = eventDetail.startTime;

                    event.end = (eventDetail.endDate && eventDetail.endTime) ? this.getEndDateTime(eventDetail.endDate, eventDetail.endTime, timeZoneDiff) : (event.start + 1);
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

                    // ('2 ', event);

                    proEvents.push(event);

                    this.checkAndInsertSlots(event, timeWindow);

                }
            }

            // this._logger.log("finatimeWindow: ", timeWindow);

            provider.events = proEvents;
            provider.multipleEvents = timeWindow;
            this.providers.push(provider);
            this.providerList.emit(this.providers);

            this.proMultiEventList = [];
            for (let i = 0; i < this.providers.length; i++) {
                let eventList: Array<Array<any>> = this.providers[i].multipleEvents;
                this.proMultiEventList.push(eventList);
            }
        }

        this.initDone = true;
        //this.ngOnChanges();
    }

    checkAndInsertSlots(event: CustomCalendarEvent, timeWindow: Array<Array<any>>) {
        let insertEvent: boolean = true;
        let startTime: number = Math.floor(event.start);
        let endTime: number = Math.floor(event.end);
        for (let rowSlots of timeWindow) {
            //this._logger.log("rowslot ", rowSlots);
            insertEvent = true;
            for (let startIndex: number = ((startTime - this.timeWindows[0]) < 0 ? 0 : (startTime - this.timeWindows[0]));
                startIndex <= (endTime <= this.timeWindows[this.timeWindows.length - 1] ? (endTime - this.timeWindows[0]) : this.timeWindows.length - 1);
                startIndex++) {
                if (rowSlots[startIndex]) {
                    insertEvent = false;
                    break;
                }
            }

            if (insertEvent) {
                rowSlots[(startTime - this.timeWindows[0]) < 0 ? 0 : (startTime - this.timeWindows[0])] = event;
                for (let startIndex: number = ((startTime + 1 - this.timeWindows[0]) <= 0 ? 1 : (startTime + 1 - this.timeWindows[0]));
                    startIndex <= (endTime <= this.timeWindows[this.timeWindows.length - 1] ? (endTime - this.timeWindows[0]) : this.timeWindows.length - 1);
                    startIndex++) {
                    rowSlots[startIndex] = 1;
                }

                break;
            }
        }

        //this._logger.log("insertEvent ", insertEvent);

        if (!insertEvent) {
            let newIndex = timeWindow.length;
            timeWindow[newIndex] = new Array<any>(this.timeWindows.length);
            for (let index = 0; index < this.timeWindows.length; index++) {
                timeWindow[newIndex][index] = 0;
            }

            timeWindow[newIndex][(startTime - this.timeWindows[0]) < 0 ? 0 : (startTime - this.timeWindows[0])] = event;
            for (let startIndex: number = ((startTime + 1 - this.timeWindows[0]) <= 0 ? 1 : (startTime + 1 - this.timeWindows[0]));
                startIndex <= (endTime <= this.timeWindows[this.timeWindows.length - 1] ? (endTime - this.timeWindows[0]) : this.timeWindows.length - 1);
                startIndex++) {
                timeWindow[newIndex][startIndex] = 1;
            }
        }

        //this._logger.log("timeWindow ", timeWindow);
        //this._logger.log(" **************************************** ");
        //this._logger.log(" ");
    }

    getStartDateTime(date: number, time: string, tzoffSet: number): number {
        let returnHour: number;
        if (date) {
            let givenDate: Date = new Date(date + (tzoffSet * 60000));
            if (startOfDay(givenDate).getTime() < startOfDay(this.selectedDate).getTime()) {
                returnHour = getHours(startOfDay(this.selectedDate));
            } else {
                returnHour = getHours(addMinutes(addHours(startOfDay(givenDate), Number.parseInt(time.split(":")[0])),
                    Number.parseInt(time.split(":")[1]) - tzoffSet));
                if (Math.abs(Number.parseInt(time.split(":")[1]) - 30) <= 10) {
                    returnHour += 0.5;
                }
            }

        } else {
            returnHour = getHours(startOfDay(this.selectedDate));
        }

        return returnHour;
    }

    getEndDateTime(date: number, time: string, tzoffSet: number): number {
        let returnHour: number;
        if (date) {
            let givenDate: Date = new Date(date + (tzoffSet * 60000) - 1);
            if (endOfDay(givenDate).getTime() > endOfDay(this.selectedDate).getTime()) {
                returnHour = getHours(endOfDay(this.selectedDate)) + 1;
            } else {
                returnHour = getHours(addMinutes(addHours(startOfDay(givenDate), Number.parseInt(time.split(":")[0])),
                    Number.parseInt(time.split(":")[1]) - tzoffSet));
                if (Math.abs(Number.parseInt(time.split(":")[1]) - 30) <= 10) {
                    returnHour += 0.5;
                }
            }
        } else {
            returnHour = getHours(endOfDay(this.selectedDate)) + 1;
        }

        // ("end date ", givenDate, time, returnHour);
        return returnHour;
    }

    getStatusString(statusCode: SOStatus) {
        this._logger.log("getting status: ", statusCode);
        let statusString: string = '';

        switch (statusCode) {
            case SOStatus.ACCEPTED:
                statusString = "Accept";
                break;

            case SOStatus.ACTIVE:
                statusString = "Active";
                break;

            case SOStatus.CANCELLED:
                statusString = "Cancelled";
                break;

            case SOStatus.CLOSED:
                statusString = "Closed";
                break;

            case SOStatus.COMPLETED:
                statusString = "Completed";
                break;

            case SOStatus.CONDITIONAL_OFFER:
                statusString = "Conditional offer";
                break;

            case SOStatus.DELETED:
                statusString = "Deleted";
                break;

            case SOStatus.DRAFT:
                statusString = "Draft";
                break;

            case SOStatus.EXPIRED:
                statusString = "Expired";
                break;

            case SOStatus.PENDING_CANCEL:
                statusString = "Pending cancel";
                break;

            case SOStatus.PROBLEM:
                statusString = "Problem";
                break;

            case SOStatus.ROUTED:
                statusString = "Posted";
                break;

            case SOStatus.VOIDED:
                statusString = "Voided";
                break;

            default:
                statusString = "NA";
                break;
        }

        return statusString;
    }

    mock() {
        // ('inside day mock');
        const dummyProviderNames = ['Alok', 'Ajay', 'Rohit', 'Vinod'];

        this.providers = [];

        for (let i in [1, 2, 3]) {
            let provider: Provider = new Provider();
            provider.id = Number.parseInt(i);
            provider.name = dummyProviderNames[i];

            let proEvents = [];
            for (let j in [1, 2, 3, 4, 5, 6]) {
                let event: CustomCalendarEvent = new CustomCalendarEvent();
                event.providerDetails = { id: provider.id, name: provider.name, location: 'location', customer: 'cust name' };
                event.cssClass = (Number.parseInt((Math.random() * 10).toFixed(0)) % 2 == 0) ? "internal-event" : "external-event";
                event.start = getHours(addHours(new Date(), Number.parseInt(((Math.random() * 100) % 23).toFixed(0))));
                event.end = event.start + Math.ceil(Number.parseInt((Math.random() * 10).toFixed(0)) % 2) + 1;
                event.serviceOrder = { soId: '580-4578-3123-43', status: SOStatus.ACTIVE };
                // (event);
                proEvents.push(event);
            }
            provider.events = proEvents;
            this.providers.push(provider);
        }

    }
}
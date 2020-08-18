import { Component, OnInit, OnChanges, Input, Output, EventEmitter } from '@angular/core';
import { LoggerUtil } from '../../common/util/logger';
import { addHours, addMinutes, getHours, startOfDay, endOfDay } from 'date-fns';
import { CustomCalendarEvent } from '../../common/modal/calendar-event.modal';
import { CalendarResponse } from '../../common/modal/calendar-response.modal';
import { HttpRestAPIService } from '../../services/http-rest-api.service';
import { addEvent } from '../../common/util/api-urls';
import { Provider } from '../../common/modal/provider.modal';

declare var jQuery: any;

@Component({
    selector: 'add-event-popup',
    templateUrl: 'add-event-popup.component.html'
})
export class AddEventPopupComponent implements OnInit {

    constructor(private _logger: LoggerUtil, private _httpRestService: HttpRestAPIService) { }

    techSelected : string = "";
    newEvent : CustomCalendarEvent;
    @Input()
    addEventSlot : number;
    @Input()
    addEventDate : Date;
    @Input()
    providerList : Array<Provider>;
    @Output()
    completedAddEvent : EventEmitter<string> = new EventEmitter<string>();

    ngOnInit() { 
        this.newEvent = new CustomCalendarEvent();
    }

    ngOnChanges() {
        this.newEvent = new CustomCalendarEvent();
        this.techSelected = "";
        if (this.addEventSlot) {
            this.newEvent.startTime = this.addEventSlot<10? "0"+this.addEventSlot + ":00": this.addEventSlot + ":00";
            this.newEvent.endTime = this.addEventSlot<9? "0"+(this.addEventSlot+1) + ":00": (this.addEventSlot+1) + ":00";
        }
    }

    closePopup() {
        //this._logger.log("Addevent popup closed");
        jQuery("#add-event-action").modal("hide");
    }

    onSelect(selectedValue: any) {
        this.techSelected = selectedValue.value;
    }

    createEvent(){
        this._logger.log("creating event for date"+this.addEventDate);
        this._logger.log(this.newEvent);
        let requestJson : string = this.getRequstJson(this.newEvent);
        try{
            this._httpRestService.postRequest(addEvent,requestJson).subscribe(
                data => {
                    let response : CalendarResponse = JSON.parse(data);
                    this.completedAddEvent.emit("success");
                    this._logger.log(response);
                },
                error => {
                    this._logger.log(error);
                },
                () => {

                }
            );
        }
        catch(error){
            this._logger.log(error);
        }
    }

    getRequstJson(newEvent: CustomCalendarEvent) : string{
        let requestJson : string = "{"
        + "'eventTitle' : '"+ newEvent.title + "',"
        + "'providerId' : '"+ this.techSelected + "',"
        + "'memberDetail' : '"+ newEvent.memberFirstName + "',"
        + "'startDate' : " + (startOfDay(this.addEventDate).getTime() + (Number.parseInt(newEvent.startTime.split(":")[0])*3600000) + (Number.parseInt(newEvent.startTime.split(":")[1])*60000)) + ","
        + "'endDate' : "+ (startOfDay(this.addEventDate).getTime() + (Number.parseInt(newEvent.endTime.split(":")[0])*3600000) + (Number.parseInt(newEvent.endTime.split(":")[1])*60000)) + ","
        + "'type' : " + "'NON_SERVICE'"
        + "}";
        this._logger.log(requestJson);
        return requestJson;
    }

}
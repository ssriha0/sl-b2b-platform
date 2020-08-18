import { Component, OnInit, Input, Output, OnChanges, EventEmitter } from '@angular/core';

import { LoggerUtil } from '../../common/util/logger';
import { SOStatus } from '../../common/util/so-status';
import { DataStorageUtil } from '../../common/util/data-storage';
import { SOActionService, SOButtons } from '../../services/so-action-button.service';
import { CustomCalendarEvent } from '../../common/modal/calendar-event.modal';

import { SOActionModal } from '../../common/modal/so-action.modal';
import { ActionButtonModal } from '../../common/modal/action-button.modal';


declare var jQuery: any;

@Component({
    selector: 'so-action-popup',
    templateUrl: 'so-action-popup.component.html'
})
export class SOActionPopupComponent implements OnInit, OnChanges {

    rejectSO: boolean;
    rejectReason: string = "";

    reschedule: boolean;
    rescheduleReason: string = "";

    actionsButton: SOButtons;

    enableActionButtons: boolean;

    @Output()
    completedAction: EventEmitter<any> = new EventEmitter<any>();

    @Output()
    showLoader: EventEmitter<boolean> = new EventEmitter<boolean>();

    @Input()
    soEvent: CustomCalendarEvent;

    buttons: Array<SOActionModal>;
    linkList: Map<SOButtons, SOActionModal>;

    constructor(private _dataUtil: DataStorageUtil, private _logger: LoggerUtil, private _soActionService: SOActionService) { }

    ngOnInit() {
        this._logger.log('popup init');
    }

    ngOnChanges() {
        if (this.soEvent) {
            this._logger.log('changed event in action popup.');

            this.soEvent.statusString = this.getStatusString(Number.parseInt(this.soEvent.status + ""));

            this.rejectSO = false;
            this.rejectReason = '';

            this.buttons = [];
            this.enableActionButtons = this._dataUtil.getStorage();
            //if (this.enableActionButtons) {
            this.linkList = this._soActionService.getProviderActions(this.soEvent.serviceOrder);
            this._logger.log("popup onchange: ", this.linkList);
            this.linkList.forEach((value) => {
                this.buttons.push(value);
            });
            //}
            this._logger.log("Edit mode: ", this.enableActionButtons, " button size: ", this.buttons.length);
        }
    }

    callBackButton(type: SOButtons) {
        this._logger.log('so popup button clicked: ', type);

        let actionButtonDetails: ActionButtonModal = new ActionButtonModal();
        actionButtonDetails.serviceOrder = this.soEvent.serviceOrder;

        if (type == SOButtons.REJECT_SO) {
            if (!this.rejectSO) {
                this.rejectSO = true;
            } else if (this.rejectReason.trim().length <= 0) {
                this._logger.log("Enter rejectReason.");
            } else {
                actionButtonDetails.rejectReasonCode = 6;
                actionButtonDetails.rejectComment = this.rejectReason;

                this.executeButtonClick(type, actionButtonDetails);
            }
        } else {
            this.executeButtonClick(type, actionButtonDetails);
        }
    }

    executeButtonClick(type: SOButtons, actionButtonDetails: ActionButtonModal) {
        this.showLoader.emit(true);
        try {
            this._soActionService.callSOAction(type, actionButtonDetails).subscribe(
                data => {
                    this._logger.log(type, data);
                    let response = JSON.parse(data);

                    this.showLoader.emit(false);

                    if (!response.result) {
                        this.fallBackMethod(actionButtonDetails);
                    } else {
                        this.completedAction.emit(data);
                    }
                },
                error => {
                    this.showLoader.emit(false);
                    this._logger.log("In error block", type, error);
                    this.fallBackMethod(actionButtonDetails);
                },
                () => {
                    this._logger.log(type, "Finished.");
                }
            );
        } catch (error) {
            this.showLoader.emit(false);
        }

        this.closePopup();
    }

    fallBackMethod(actionButtonDetails: ActionButtonModal) {
        this._logger.log("Falling back to SO Monitor.");

        this.showLoader.emit(true);
        this._soActionService.callSOAction(SOButtons.DEFAULT, actionButtonDetails).subscribe(
            data => {
                this.showLoader.emit(false);
                this._logger.log(SOButtons.DEFAULT, data);
            },
            error => {
                this.showLoader.emit(false);
                this._logger.log(SOButtons.DEFAULT, error);
            },
            () => {
                this._logger.log(SOButtons.DEFAULT, "Finished.");
            }
        );
    }

    closePopup() {
        this._logger.log("Action popup closed");
        jQuery("#so-action").modal("hide");
        this.rejectSO = false;
        this.rejectReason = '';
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
}
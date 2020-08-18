import { Injectable } from '@angular/core';
import { SOStatus } from '../common/util/so-status';
import { LoggerUtil } from '../common/util/logger';
import { actionRedirectPage, acceptSO, rejectSO } from '../common/util/api-urls';

import { HttpRestAPIService } from './http-rest-api.service';

import { ServiceOrderModal } from '../common/modal/service-order.modal';
import { SOActionModal } from '../common/modal/so-action.modal';
import { ActionButtonModal } from '../common/modal/action-button.modal';
import { CalendarResponse } from '../common/modal/calendar-response.modal';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

@Injectable()
export class SOActionService {
    linkActionsMap: Map<SOButtons, SOActionModal> = new Map();
    soActionMethodMap: Map<SOButtons, Function> = new Map();

    constructor(private _logger: LoggerUtil, private _httpService: HttpRestAPIService) {
        this.linkActionsMap.set(SOButtons.ACCEPT_SO, new SOActionModal(SOButtons.ACCEPT_SO, "Accept", "acceptSO", null, null));
        this.linkActionsMap.set(SOButtons.ADMIN_ACCEPT_SO, new SOActionModal(SOButtons.ADMIN_ACCEPT_SO, "Accept", "adminAcceptSO", null, null));
        this.linkActionsMap.set(SOButtons.ASSIGN_SO, new SOActionModal(SOButtons.ASSIGN_SO, "Assign", "assignSO", null, null));
        this.linkActionsMap.set(SOButtons.REQUEST_RESCHEDULE_SO, new SOActionModal(SOButtons.REQUEST_RESCHEDULE_SO, "Reschedule Visit", "requestRescheduleSO", null, null));
        this.linkActionsMap.set(SOButtons.CANCEL_RESCHEDULE_SO, new SOActionModal(SOButtons.CANCEL_RESCHEDULE_SO, "Cancel Reschedule", "cancelRescheduleSO", null, null));
        this.linkActionsMap.set(SOButtons.EDIT_RESCHEDULE_SO, new SOActionModal(SOButtons.EDIT_RESCHEDULE_SO, "Edit Reschedule", "editRescheduleSO", null, null));
        this.linkActionsMap.set(SOButtons.RE_ASSIGN_SO, new SOActionModal(SOButtons.RE_ASSIGN_SO, "Re-Assign", "reAssignSO", null, null));
        this.linkActionsMap.set(SOButtons.REJECT_SO, new SOActionModal(SOButtons.REJECT_SO, "Reject", "rejectSO", null, null));
        this.linkActionsMap.set(SOButtons.RELEASE_SO, new SOActionModal(SOButtons.RELEASE_SO, "Release", "releaseSO", null, null));
        this.linkActionsMap.set(SOButtons.REPORT_A_PROBLEM_SO, new SOActionModal(SOButtons.REPORT_A_PROBLEM_SO, "Report a Problem", "reportAProblemSO", null, null));

        this.soActionMethodMap.set(SOButtons.ACCEPT_SO, this.acceptSO.bind(this));
        this.soActionMethodMap.set(SOButtons.ADMIN_ACCEPT_SO, this.adminAcceptSO.bind(this));
        this.soActionMethodMap.set(SOButtons.ASSIGN_SO, this.assignSO.bind(this));
        this.soActionMethodMap.set(SOButtons.REQUEST_RESCHEDULE_SO, this.requestRescheduleSO.bind(this));
        this.soActionMethodMap.set(SOButtons.CANCEL_RESCHEDULE_SO, this.cancelRescheduleSO.bind(this));
        this.soActionMethodMap.set(SOButtons.EDIT_RESCHEDULE_SO, this.editRescheduleSO.bind(this));
        this.soActionMethodMap.set(SOButtons.RE_ASSIGN_SO, this.reAssignSO.bind(this));
        this.soActionMethodMap.set(SOButtons.REJECT_SO, this.rejectSO.bind(this));
        this.soActionMethodMap.set(SOButtons.RELEASE_SO, this.releaseSO.bind(this));
        this.soActionMethodMap.set(SOButtons.REPORT_A_PROBLEM_SO, this.reportAProblemSO.bind(this));
        this.soActionMethodMap.set(SOButtons.DEFAULT, this.defaultAction.bind(this));
    }

    public getProviderActions(soOrder: ServiceOrderModal): Map<SOButtons, SOActionModal> {
        let linkList: Map<SOButtons, SOActionModal> = new Map();
        let status: SOStatus = soOrder.status;

        switch (status) {
            case SOStatus.ACCEPTED:
                linkList.set(SOButtons.RELEASE_SO, this.linkActionsMap.get(SOButtons.RELEASE_SO));

                if (this.isRescheduleButton(soOrder)) {
                    linkList.set(SOButtons.CANCEL_RESCHEDULE_SO, this.linkActionsMap.get(SOButtons.CANCEL_RESCHEDULE_SO));
                    linkList.set(SOButtons.EDIT_RESCHEDULE_SO, this.linkActionsMap.get(SOButtons.EDIT_RESCHEDULE_SO));
                } else {
                    linkList.set(SOButtons.REQUEST_RESCHEDULE_SO, this.linkActionsMap.get(SOButtons.REQUEST_RESCHEDULE_SO));
                }

                if (this.isPrimary()) {
                    if (soOrder.assignmentType && soOrder.assignmentType === "FIRM") {
                        linkList.set(SOButtons.ASSIGN_SO, this.linkActionsMap.get(SOButtons.ASSIGN_SO));
                    } else {
                        linkList.set(SOButtons.RE_ASSIGN_SO, this.linkActionsMap.get(SOButtons.RE_ASSIGN_SO));
                    }
                }

                break;
            case SOStatus.ACTIVE:
                linkList.set(SOButtons.RELEASE_SO, this.linkActionsMap.get(SOButtons.RELEASE_SO));
                linkList.set(SOButtons.REPORT_A_PROBLEM_SO, this.linkActionsMap.get(SOButtons.REPORT_A_PROBLEM_SO));

                if (this.isRescheduleButton(soOrder)) {
                    linkList.set(SOButtons.CANCEL_RESCHEDULE_SO, this.linkActionsMap.get(SOButtons.CANCEL_RESCHEDULE_SO));
                    linkList.set(SOButtons.EDIT_RESCHEDULE_SO, this.linkActionsMap.get(SOButtons.EDIT_RESCHEDULE_SO));
                } else {
                    linkList.set(SOButtons.REQUEST_RESCHEDULE_SO, this.linkActionsMap.get(SOButtons.REQUEST_RESCHEDULE_SO));
                }

                if (this.isPrimary()) {
                    if (soOrder.assignmentType && soOrder.assignmentType === "FIRM") {
                        linkList.set(SOButtons.ASSIGN_SO, this.linkActionsMap.get(SOButtons.ASSIGN_SO));
                    } else {
                        linkList.set(SOButtons.RE_ASSIGN_SO, this.linkActionsMap.get(SOButtons.RE_ASSIGN_SO));
                    }
                }

                break;
            case SOStatus.CLOSED:

                break;
            case SOStatus.COMPLETED:
                linkList.set(SOButtons.REPORT_A_PROBLEM_SO, this.linkActionsMap.get(SOButtons.REPORT_A_PROBLEM_SO));

                break;
            case SOStatus.PROBLEM:
                linkList.set(SOButtons.RELEASE_SO, this.linkActionsMap.get(SOButtons.RELEASE_SO));

                if (this.isRescheduleButton(soOrder)) {
                    linkList.set(SOButtons.CANCEL_RESCHEDULE_SO, this.linkActionsMap.get(SOButtons.CANCEL_RESCHEDULE_SO));
                    linkList.set(SOButtons.EDIT_RESCHEDULE_SO, this.linkActionsMap.get(SOButtons.EDIT_RESCHEDULE_SO));
                } else {
                    linkList.set(SOButtons.REQUEST_RESCHEDULE_SO, this.linkActionsMap.get(SOButtons.REQUEST_RESCHEDULE_SO));
                }

                if (this.isPrimary()) {
                    if (soOrder.assignmentType && soOrder.assignmentType === "FIRM") {
                        linkList.set(SOButtons.ASSIGN_SO, this.linkActionsMap.get(SOButtons.ASSIGN_SO));
                    } else {
                        linkList.set(SOButtons.RE_ASSIGN_SO, this.linkActionsMap.get(SOButtons.RE_ASSIGN_SO));
                    }
                }

                break;
            case SOStatus.ROUTED:
                if (soOrder.buyerRefValue !== 'ESTIMATION' && soOrder.orderType != "2") {
                    if (this.isPrimary()) {
                        linkList.set(SOButtons.ADMIN_ACCEPT_SO, this.linkActionsMap.get(SOButtons.ADMIN_ACCEPT_SO));
                    } else {
                        linkList.set(SOButtons.ACCEPT_SO, this.linkActionsMap.get(SOButtons.ACCEPT_SO));
                    }
                }

                if (soOrder.orderType != "2") {
                    linkList.set(SOButtons.REJECT_SO, this.linkActionsMap.get(SOButtons.REJECT_SO));
                }

                break;
            case SOStatus.CANCELLED:

                break;
            case SOStatus.PENDING_CANCEL:

                break;
            default:

                break;
        }


        return linkList;
    }

    public callSOAction(button: SOButtons, actionButtonDetails: ActionButtonModal): Observable<any> {
        this._logger.log('callSOAction called');
        return this.soActionMethodMap.get(button)(actionButtonDetails);
    }

    private acceptSO(actionButtonDetails: ActionButtonModal): Observable<any> {
        this._logger.log('acceptSO called');
        return this.commonAccept(actionButtonDetails);
    }
    private adminAcceptSO(actionButtonDetails: ActionButtonModal): Observable<any> {
        this._logger.log('adminAcceptSO called');
        return this.commonAccept(actionButtonDetails);
    }

    private commonAccept(actionButtonDetails: ActionButtonModal): Observable<any> {
        this._logger.log('commonAccept called');

        return this._httpService.postRequest(acceptSO, {
            'soId': actionButtonDetails.serviceOrder.soId,
            'routedResourceId': actionButtonDetails.serviceOrder.personId,
            'startDate': actionButtonDetails.serviceOrder.startDate,
            'startTime': actionButtonDetails.serviceOrder.startTime,
            'endDate': actionButtonDetails.serviceOrder.endDate,
            'endTime': actionButtonDetails.serviceOrder.endTime,
        });
    }
    private assignSO(actionButtonDetails: ActionButtonModal): Observable<any> {
        this._logger.log('assignSO called');
        return this.defaultAction(actionButtonDetails);
    }
    private requestRescheduleSO(actionButtonDetails: ActionButtonModal): Observable<any> {
        this._logger.log('rescheduleRescheduleSO called');
        return this.defaultAction(actionButtonDetails);
    }
    private cancelRescheduleSO(actionButtonDetails: ActionButtonModal): Observable<any> {
        this._logger.log('cancelRescheduleSO called');
        return this.defaultAction(actionButtonDetails);
    }
    private editRescheduleSO(actionButtonDetails: ActionButtonModal): Observable<any> {
        this._logger.log('editRescheduleSO called');
        return this.defaultAction(actionButtonDetails);
    }
    private reAssignSO(actionButtonDetails: ActionButtonModal): Observable<any> {
        this._logger.log('reAssignSO called');
        return this.defaultAction(actionButtonDetails);
    }
    private rejectSO(actionButtonDetails: ActionButtonModal): Observable<any> {
        this._logger.log('rejectSO called');

        return this._httpService.postRequest(rejectSO, {
            'soId': actionButtonDetails.serviceOrder.soId,
            'routedResourceId': actionButtonDetails.serviceOrder.personId,
            'rejectReasonId': actionButtonDetails.rejectReasonCode,
            'rejectReasonComment': actionButtonDetails.rejectComment
        });
    }
    private releaseSO(actionButtonDetails: ActionButtonModal): Observable<any> {
        this._logger.log('releaseSO called');
        return this.defaultAction(actionButtonDetails);
    }
    private reportAProblemSO(actionButtonDetails: ActionButtonModal): Observable<any> {
        this._logger.log('reportAProblemSO called');
        return this.defaultAction(actionButtonDetails);
    }

    private defaultAction(actionButtonDetails: ActionButtonModal): Observable<any> {
        this._logger.log('defaultAction called');
        let url = actionRedirectPage + "?soId=" + actionButtonDetails.serviceOrder.soId;
        window.location.href = url;

        let calendarResponse: CalendarResponse = new CalendarResponse();
        calendarResponse.result = {};
        return Observable.of(calendarResponse).map(response => JSON.stringify(response));
    }

    private isRescheduleButton(soOrder: ServiceOrderModal): boolean {
        let status: SOStatus = soOrder.status;
        let isReschedule: boolean = false;
        if (soOrder.rescheduleDate && (status === SOStatus.ACCEPTED || status === SOStatus.ACTIVE || status === SOStatus.PROBLEM)) {
            isReschedule = true;
        }
        return isReschedule;
    }

    private isPrimary(): boolean {
        return true;
    }
}

export enum SOButtons {
    DEFAULT = 99,
    RELEASE_SO = 1,
    ADMIN_ACCEPT_SO = 2,
    ACCEPT_SO = 3,
    CANCEL_RESCHEDULE_SO = 4,
    EDIT_RESCHEDULE_SO = 5,
    RE_ASSIGN_SO = 6,
    ASSIGN_SO = 7,
    REPORT_A_PROBLEM_SO = 8,
    REJECT_SO = 9,
    REQUEST_RESCHEDULE_SO = 10,
}
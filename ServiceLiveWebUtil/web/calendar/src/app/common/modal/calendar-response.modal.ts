export class CalendarResponse {
    error?: any;
    result?: any;
    editable?: boolean;
    results?: any;
}

export class EventResponse {

    firmId: number;
    personId?: number; // provider id
    personDetail: ProviderDetail;

    type?: string

    createdBy?: string;
    createdDate?: number;
    endDate?: number;
    endTime?: string;
    eventId: string;
    eventName?: string;

    memberCity?: string;
    memberFirstName?: string;
    memberLastName?: string;
    memberState?: string;
    memberZip?: string;
    modifiedBy?: string;
    modifiedDate?: number;

    source?: string;  // ServiceLive
    startDate?: number;
    startTime?: string;
    status?: number; // SOStatus
}

export class ProviderDetail {
    personId: number;
    personFirstName: string;
    personLastName: string;
    personPrimaryInd: number;
    personAdminInd: number;
    personWFStateId: number;
    personWFState: number;
}
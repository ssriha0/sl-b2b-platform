import { ProviderDetailsModal } from './provider-details.modal';
import { ServiceOrderModal } from './service-order.modal';

export class CustomCalendarEvent {
    id:string;
    providerDetails: ProviderDetailsModal;
    serviceOrder : ServiceOrderModal;
    start: any;
    end?: any;
    title: string;
    color: any;
    actions?: any[];
    cssClass?: string;
    draggable?: boolean;
    meta?: any;

    createdBy?: string;
    createdDate?: number;
    endDate?: number;
    endTime?: string;
    eventId: number;
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
    statusString?: string; // SOStatus
    type?: string;
}
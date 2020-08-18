import { SOStatus } from '../util/so-status';
export class ServiceOrderModal {
    soId: string;
    soName?: string;
    status: SOStatus;
    rescheduleDate?: string;
    assignmentType?: string; // FIRM
    buyerRefValue?: string; // ESTIMATION
    orderType?: string; //  2 - ORDER_TYPE_CHILD
    personId?: number;
    startDate?: number;
    startTime?: string;
    endDate?: number;
    endTime?: string;
}
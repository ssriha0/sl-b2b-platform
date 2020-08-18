import { ErrorResponse } from '../results-modal';
export class RateCardResponseModal {
    error: ErrorResponse;
    result: ServiceDayRatePeriod;
}
export class ServiceDayRatePeriod {
    serviceDaysVOs: Array<ServiceDay>;
    serviceRatePeriodVOs: Array<ServiceRatePeriod>
}

export class ServiceDay {
    id: number;
    name: string;
}

export class ServiceRatePeriod {
    id: number;
    name: string;
}
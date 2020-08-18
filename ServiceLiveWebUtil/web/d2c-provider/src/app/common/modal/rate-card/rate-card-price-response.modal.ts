import { ErrorResponse } from '../results-modal';
export class RateCardPriceResponseModal {
    error: ErrorResponse;
    result: Array<RateCardPrice>;
}

export class RateCardPrice {
    vendorCategoryPriceId: number;
    price: number;
    serviceDayVOs: ServiceDayVO;
    serviceRatePeriodVO: ServiceRatePeriodVO;
}

export class ServiceDayVO {
    id: number;
}

export class ServiceRatePeriodVO {
    id: number;
}
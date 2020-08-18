export class RateCardPriceUIModal {
    rateCard: Array<RatePriceUIModal>;
}
export class RatePriceUIModal {
    vendorCategoryPriceId: number;
    price: string;
    priceValid: boolean = true;
    serviceDayVOs: ServiceDayUIVO;
    serviceRatePeriodVO: ServiceRatePeriodUIVO;
}

export class ServiceDayUIVO {
    id: number;
}

export class ServiceRatePeriodUIVO {
    id: number;
}

export class RateCardEventEmmiter{
    rateCardAdded:string;
    primaryIndustryId:string;
}